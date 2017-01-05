package com.qlp.core.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.qlp.core.util.AssertUtil;

public class PageImpl<T> implements Page<T> {
	
	private final List<T> content = new ArrayList<>();
	private final Pageable<T> pageable;
	
	public PageImpl(List<T> content,Pageable<T> pageable){
		AssertUtil.assertNotNull(content, "分页对象不能为null");
		AssertUtil.assertNotNull(pageable, "分页条件不能为null");
		
		this.content.addAll(content);
		this.pageable = pageable;
	}

	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}

	@Override
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	@Override
	public int getPageSize() {
		return pageable.getPageSize();
	}

	@Override
	public int getCurrentPage() {
		return pageable.getCurrentPage();
	}

	@Override
	public int getTotalPage() {
		return (int)Math.floor(getTotalElements() / getPageSize()) + 1;
	}

	@Override
	public long getTotalElements() {
		return pageable.getTotalElement();
	}

}
