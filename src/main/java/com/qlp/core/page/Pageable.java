package com.qlp.core.page;


public interface Pageable<T> {
	
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
	 * 偏移量
	 * @return
	 */
	int getOffset();
	
	/**
	 * 总记录数
	 * @return
	 */
	long getTotalElement();
	
	/**
	 * 排序条件
	 * @return
	 */
	Sort getSort();
	
	/**
	 * 查询条件
	 */
	T getParams();
	
	

}
