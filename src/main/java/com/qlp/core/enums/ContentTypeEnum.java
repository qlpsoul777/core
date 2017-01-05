package com.qlp.core.enums;

/**
 * 页面内容类型
 * @author july
 *
 */
public enum ContentTypeEnum {
	
	HTML(0,"HTML"),
	JSP(1,"JSP"),
	XML(2,"XML");
	
	private final int index;
	private final String desc;
	
	ContentTypeEnum(int index,String desc){
		this.index = index;
		this.desc = desc;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public String getDesc(){
		return this.desc;
	}
}
