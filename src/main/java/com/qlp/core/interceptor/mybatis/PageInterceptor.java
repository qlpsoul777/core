package com.qlp.core.interceptor.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import com.qlp.core.page.Pageable;
import com.qlp.core.util.ReflectionUtil;

/**
 * mybatis分页插件拦截器
 * @author qlp
 *
 */
@Intercepts({@Signature(args = { Connection.class }, method = "prepare", type = StatementHandler.class)})
public class PageInterceptor implements Interceptor{
	
	private String dataBaseType;//数据库类型

	@SuppressWarnings("unused")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//1、获取代理对象
		RoutingStatementHandler rsHandler = (RoutingStatementHandler) invocation.getTarget();
		
		//2、从代理对象中获取sql信息，即mapper配置文件中查询sql的参数
		BoundSql boundSql = rsHandler.getBoundSql();
		
		//3、获取分页参数
		Object obj=boundSql.getParameterObject();
		if(obj instanceof Pageable<?>){
			Pageable<?> pageable = (Pageable<?>) obj;
			
			//4、获取代理对象的参数，即prepare方法的参数
			Connection connection=(Connection)invocation.getArgs()[0];
			
			//5、获取已定义的查询sql语句
			String sql = boundSql.getSql();
			
			//6、查询出符合条件的总记录数，并设值到pageable对象中
			long totals = getTotalElement();
			ReflectionUtil.setFieldValue(pageable, "totalElement", totals);
			
			//7、拼装分页查询sql语句，并设值到boundSql对象中
			String pageSql = getPageSql(sql);
			ReflectionUtil.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}

	private String getPageSql(String sql) {
		StringBuilder pageSql = new StringBuilder(sql.length() + 20);
		pageSql.append(sql);
		if("mysql".equalsIgnoreCase(this.dataBaseType)){
			
		}else if("oracle".equalsIgnoreCase(this.dataBaseType)){
			
		}
		return pageSql.toString();
	}

	private long getTotalElement() {
		// TODO Auto-generated method stub
		return 0;
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
