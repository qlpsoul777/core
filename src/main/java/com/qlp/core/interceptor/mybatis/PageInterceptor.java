package com.qlp.core.interceptor.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.qlp.core.exception.MyException;
import com.qlp.core.page.Pageable;
import com.qlp.core.page.Sort;
import com.qlp.core.page.Sort.Order;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.IoUtil;
import com.qlp.core.util.ReflectionUtil;

/**
 * mybatis分页插件拦截器
 * @author qlp
 *
 */
@Intercepts({@Signature(args = { Connection.class }, method = "prepare", type = StatementHandler.class)})
public class PageInterceptor implements Interceptor{
	
	private String dataBaseType;//数据库类型

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//1、获取代理对象
		RoutingStatementHandler rsHandler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) ReflectionUtil.getFieldValue(rsHandler,"delegate");
		
		//2、从代理对象中获取sql信息，即mapper配置文件中查询sql的参数
		BoundSql boundSql = delegate.getBoundSql();
		
		//3、获取分页参数
		Object obj=boundSql.getParameterObject();
		if(obj instanceof Pageable<?>){
			Pageable<?> pageable = (Pageable<?>) obj;
			
			//4、获取代理对象的参数，即prepare方法的参数
			Connection connection = (Connection)invocation.getArgs()[0];
			
			//5、获取已定义的查询sql语句
			String sql = boundSql.getSql();
			
			//6、查询出符合条件的总记录数，并设值到pageable对象中
			MappedStatement mappedStatement = (MappedStatement)ReflectionUtil.getFieldValue(delegate,"mappedStatement");
			setTotalElement(mappedStatement,pageable,connection);

			
			//7、拼装分页查询sql语句，并设值到boundSql对象中
			String pageSql = getPageSql(sql,pageable);
			ReflectionUtil.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}

	private String getPageSql(String sql,Pageable<?> pageable) {
		StringBuilder pageSql = new StringBuilder(sql.length() + 20);
		pageSql.append(sql);
		
		//判断原sql语句是否已经有排序了，如果没有就从分页参数中获取排序条件，拼接到sql后，如果已有，跳过不覆盖原sql的排序
		if(!StringUtils.containsIgnoreCase(sql, "ORDER BY")){
			Sort sort = pageable.getSort();
			if(sort != null){
				List<Order> orders = sort.getOrders();
				if(CollectionUtil.isNotBlank(orders)){
					pageSql.append(" ORDER BY");
					for (Order order : orders) {
						pageSql.append(" " + order.getProperty() + " " + order.getDirection().name());
					}
				}
			}
		}
				
		if("mysql".equalsIgnoreCase(this.dataBaseType)){
			pageSql.append(" LIMIT ").append(pageable.getOffset()).append(",").append(pageable.getPageSize() + pageable.getOffset());
		}else if("oracle".equalsIgnoreCase(this.dataBaseType)){
			
		}
		return pageSql.toString();
	}

	private void setTotalElement(MappedStatement mappedStatement,Pageable<?> pageable,Connection connection) {
		BoundSql boundSql = mappedStatement.getBoundSql(pageable);
		String sql = "SELECT COUNT(0) FROM (" + boundSql.getSql() +") as total";
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),sql,parameterMappings,pageable);
		
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, pageable, countBoundSql);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			parameterHandler.setParameters(ps);
			rs = ps.executeQuery();
			if(rs!=null && rs.next()) {
				ReflectionUtil.setFieldValue(pageable, "totalElement", rs.getInt(1));
			}
		}catch(Exception e){
			throw  new MyException(e,"");
		}finally {
			IoUtil.close(ps,rs);//此处千万不要将connection关闭了
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		this.dataBaseType = properties.getProperty("dataBaseType");
	}

}
