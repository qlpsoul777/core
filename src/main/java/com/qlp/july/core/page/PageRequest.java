package com.qlp.july.core.page;

import java.io.Serializable;
import java.util.Map;

import com.qlp.july.core.util.AssertUtil;


public class PageRequest implements Pageable,Serializable{

	private static final long serialVersionUID = -7811548451180065787L;
	
	private final int pageSize;
	private final int currentPage;
	
	public PageRequest(int pageSize,int currentPage){
		AssertUtil.assertTrue(pageSize >= 1, "每页显示记录数不能小于1");
		AssertUtil.assertTrue(currentPage >= 0, "当前页不能小于0");
		
		this.pageSize = pageSize;
		this.currentPage = currentPage;
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
	public Sort getSort() {
		return null;
	}

	@Override
	public Map<String, Object> getParams() {
		return null;
	}
	
}
