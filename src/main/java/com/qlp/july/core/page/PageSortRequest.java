package com.qlp.july.core.page;

import java.util.Map;

import com.qlp.july.core.util.AssertUtil;

public class PageSortRequest extends PageRequest{
	
	private final Sort sort;
	
	public PageSortRequest(int pageSize,int currentPage,Sort sort){
		super(pageSize, currentPage);
		
		AssertUtil.assertNotNull(sort, "排序对象不能为null");
		this.sort = sort;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Map<String, Object> getParams() {
		return null;
	}

}
