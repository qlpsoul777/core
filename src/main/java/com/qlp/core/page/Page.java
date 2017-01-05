package com.qlp.core.page;

import java.util.List;

/**
 * 分页对象接口
 * 	类层次： interface Page<T>
 * 			---> PageImpl<T>实现Page接口，聚合Pageable接口
 * @author qlp
 * @param <T>
 */
public interface Page<T> {
	
	/**
	 * 判断分页数据是否为null
	 * @return
	 */
	boolean hasContent();
	
	/**
	 * 分页数据
	 * @return
	 */
	List<T> getContent();
	
	/**
	 * 每页显示条数
	 * @return
	 */
	int getPageSize();
	
	/**
	 * 当前页(从0开始计)
	 * @return
	 */
	int getCurrentPage();
	
	/**
	 * 总页数
	 * @return
	 */
	int getTotalPage();
	
	/**
	 * 数据总数
	 * @return
	 */
	long getTotalElements();

}
