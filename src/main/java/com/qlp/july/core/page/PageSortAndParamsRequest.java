package com.qlp.july.core.page;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.qlp.july.core.util.AssertUtil;
import com.qlp.july.core.util.CollectionUtil;

/**
 * 
 * @author qlp
 *
 */
public class PageSortAndParamsRequest extends PageSortRequest {
	
	private final Map<String,Object> params = new HashMap<>(8);
	
	public PageSortAndParamsRequest(int pageSize,int currentPage,Sort sort,Map<String,Object> params){
		super(pageSize, currentPage, sort);
		
		AssertUtil.assertTrue(CollectionUtil.isNotBlank(params), "查询参数不能为blank");
		this.params.putAll(params);
	}
	
	public Map<String,Object> getParams(){
		return Collections.unmodifiableMap(params);
	}

}
