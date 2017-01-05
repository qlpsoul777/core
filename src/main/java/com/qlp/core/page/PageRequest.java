package com.qlp.core.page;

import com.qlp.core.entity.BaseEntity;
import com.qlp.core.util.AssertUtil;


public class PageRequest<T> extends BaseEntity implements Pageable<T>{

	private static final long serialVersionUID = -7811548451180065787L;
	
	private final int pageSize;
	private final int currentPage;
	private final Sort sort;
	private final T params;
	
	private long totalElement;
	
	public PageRequest(int pageSize,int currentPage,Sort sort,T params){
		AssertUtil.assertTrue(pageSize >= 1, "每页显示记录数不能小于1");
		AssertUtil.assertTrue(currentPage >= 0, "当前页不能小于0");
		
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.sort = sort;
		this.params = params;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	@Override
	public int getOffset() {
		return currentPage * pageSize;
	}
	
	@Override
	public T getParams() {
		return params;
	}

	@Override
	public long getTotalElement() {
		return totalElement;
	}
	
	@Override
	public Sort getSort() {
		return sort;
	}
	
}
