package com.qlp.core.orm.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.qlp.core.exception.ErrorDetail.BusiErrorEnum;
import com.qlp.core.util.AssertUtil;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.ReflectionUtil;

/**
 * 数据操作实现类MyRepositoryImpl
 * 	--->继承SimpleJpaRepository(实现JpaRepository申明的方法)
 * 	--->实现MyRepository接口自定义的方法
 * 
 * @author qlp
 * @param <T> 持久化对象实体 eg:User user;
 * @param <ID> 实体主键类型eg:String,Long...
 */
@NoRepositoryBean
@SuppressWarnings("unchecked")
public class MyRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T,ID>{

	private final EntityManager em;
	private final Class<T> clazz;
	
	public MyRepositoryImpl(JpaEntityInformation<T,ID> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
		this.clazz = entityInformation.getJavaType();
	}

	/**
	 * 通过map参数查询列表
	 * 	map中的key值是对应实体对象的属性加后缀的形式
	 */
	
	@Override
	public List<T> queryByMap(Map<String, Object> map) {
		Criteria criteria = mapToCriteria(map);
		return (List<T>)criteria.list();
	}
	
	@Override
	public List<T> queryByMap(Map<String, Object> map, Sort sort) {
		Criteria criteria = mapToCriteria(map);
		createCriteria(criteria,sort);
		return (List<T>)criteria.list();
	}
	
	@Override
	public Page<T> queryPageByMap(Map<String, Object> map, Pageable pageable) {
		Criteria c = mapToCriteria(map);
        long total = countCriteriaList(c);
        c.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        createCriteria(c, pageable.getSort());
        return new PageImpl<>(c.list(), pageable, total);
	}

	private Criteria createCriteria(Criteria criteria, Sort sort) {
        Iterator<?> it;
        if (sort != null) {
            for (it = sort.iterator(); it.hasNext(); ) {
                Sort.Order order = (Sort.Order) it.next();
                if (order.getDirection().equals(Sort.Direction.ASC)) {
                    criteria.addOrder(org.hibernate.criterion.Order.asc(order.getProperty()));
                } else {
                    criteria.addOrder(org.hibernate.criterion.Order.desc(order.getProperty()));
                }
            }
        }
        return criteria;
    }
	

	private long countCriteriaList(Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer resultTransformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries;
        orderEntries = (List<OrderEntry>)ReflectionUtil.getFieldValue(impl, "orderEntries");
        ReflectionUtil.setFieldValue(impl, "orderEntries", new ArrayList<>());
        //获取记录总数
        Long totalCount = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long total = totalCount != null ? totalCount : 0L;
        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (resultTransformer != null) {
            c.setResultTransformer(resultTransformer);
        }
        ReflectionUtil.setFieldValue(impl, "orderEntries", orderEntries);
        return total;
	}

	/**
	 * map参数转Criteria对象
	 * @param map
	 * @return
	 */
	private Criteria mapToCriteria(Map<String, Object> map) {
		Criteria criteria = createCriteria();
		
		if(CollectionUtil.isNotBlank(map)){
			int conNum = 0,disNum = 0;
			Conjunction conjunction = Restrictions.conjunction();
			Disjunction disjunction = Restrictions.disjunction();
			
			for(String key : map.keySet()){
				String[] exp = StringUtils.split(key,"_");
				String suffix = null;
				if(exp.length == 3){
					suffix = exp[2];
				}
				if("a".equals(exp[0])){
					conjunction.add(addPredicates(map.get(key),exp[1],suffix));
					conNum++;
				}else{
					disjunction.add(addPredicates(map.get(key),exp[1],suffix));
					disNum++;
				}
			}
			
			AssertUtil.assertTrue((conNum==0), BusiErrorEnum.INPUT_NOT_EXIST, "查询条件不合法,查询条件不能都是OR");
			
			if(disNum == 0){
				criteria.add(conjunction);
			}else{
				criteria.add(Restrictions.or(conjunction,disjunction));
			}
		}
		
		return criteria;
	}
	
	/**
	 * 
	 * Criterion 是 Criteria 的查询条件。Criteria 提供了 add(Criterion criterion) 方法来添加查询条件。
	 * Criterion 接口的主要实现包括： Example 、 Junction 和 SimpleExpression 。
	 * 而Junction 的实际使用是它的两个子类 conjunction 和 disjunction ，
	 * 分别是使用 AND 和 OR 操作符进行来联结查询条件集合。Criterion 的实例可以通过 Restrictions工具类
	 * 来创建，Restrictions 提供了大量的静态方法，如 eq （等于）、 ge （大于等于）、 between 等方法的
	 * 创建 Criterion 查询条件（SimpleExpression 实例）。除此之外， Restrictions 还提供了方法来
	 * 创建 conjunction 和disjunction 实例，通过往该实例的 add(Criteria) 方法来增加查询条件形成
	 * 一个查询条件集合。
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	

	/**
	 * 添加查询条件
	 * @param object 传入条件值
	 * @param proName 属性名
	 * @param suffix 后缀
	 * 	无 | eq---> 相等
	 * 	ne 	---> 不相等
	 * 	ne 	---> 不相等
	 * 	ne 	---> 不相等
	 * 	ne 	---> 不相等
	 * 	ne 	---> 不相等
	 * 	ne 	---> 不相等
	 * @return
	 */
	private Criterion addPredicates(Object object, String proName, String suffix) {
		if(StringUtils.isBlank(suffix) || StringUtils.equals("eq", suffix)){
			return Restrictions.eq(proName, object);//相等
		}
		if (StringUtils.equals("ne", suffix)) {
            return Restrictions.ne(proName, object);  //不相等
        }
        if (StringUtils.equals("lt", suffix)) {
            return Restrictions.lt(proName, object);  //小于
        }
        if (StringUtils.equals("le", suffix)) {
            return Restrictions.le(proName, object);  //小于等于
        }
        if (StringUtils.equals("gt", suffix)) {
            return Restrictions.gt(proName, object);  //大于
        }
        if (StringUtils.equals("ge", suffix)) {
            return Restrictions.ge(proName, object);  //大于等于
        }
        if (StringUtils.equals("li", suffix)) {
            return Restrictions.like(proName, "%" + object + "%");  //like
        }
        if (StringUtils.equals("nl", suffix)) {
            return Restrictions.not(Restrictions.like(proName, "%" + object + "%"));  //not like
        }
        if (StringUtils.equals("in", suffix)) {
            return Restrictions.in(proName, object);  //in
        }
        if (StringUtils.equals("ni", suffix)) {
            return Restrictions.not(Restrictions.in(proName, object)); //not in
        }
		return null;
	}

	/**
	 * 获取在线Criteria，从session获取；
	 * hibernate还提供离线的DetachedCriteria，创建时无需session,通过自身的静态方法获取。
	 * @return
	 */
	public Criteria createCriteria() {
		return getSession().createCriteria(clazz);
	}
	 
	/**
	 * 从EntityManager获取hibernate的session
	 * @return
	 */
	private Session getSession() {
		return (Session) this.em.getDelegate();
	}

	@Override
	public List<T> queryByCriteria() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<T> query = (CriteriaQuery<T>) builder.createQuery();
		
		Root<T> root = query.from(this.clazz);
		query.select(root);
		
		Predicate p = builder.and(builder.equal(root.get("code"), "AFG"));
		p = builder.or(p,builder.equal(root.get("code"), "BRA"));
		
		
		return this.em.createQuery(query.where(p)).getResultList();
		/*Criteria criteria = createCriteria();
		criteria.add(Restrictions.and(Restrictions.eq("code", "AFG")));
		criteria.add(Restrictions.or(Restrictions.eq("code", "BRA")));
		return criteria.list();*/
	}

	


}


