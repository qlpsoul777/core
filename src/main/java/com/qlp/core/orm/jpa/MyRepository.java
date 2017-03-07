package com.qlp.core.orm.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 自定义数据操作接口
 * 接口层次是Repository
 * 	--->CrudRepository
 * 		--->PagingAndSortingRepository
 * 			--->JpaRepository
 * 				--->MyRepository
 * 
 * @author qlp
 * @param <T> 持久化对象实体 eg:User user;
 * @param <ID> 实体主键类型eg:String,Long...
 */
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID>{
	
	public abstract List<T> queryByCriteria();
	
	public abstract List<T> queryByMap(Map<String, Object> map);
	
	public abstract List<T> queryByMap(Map<String, Object> map,Sort sort);
	
	public abstract Page<T> queryPageByMap(Map<String, Object> map,Pageable pageable);
}

