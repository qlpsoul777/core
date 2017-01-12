package com.qlp.core.enums;

/**
 * 页面内容类型
 * @author july
 *
 */
public enum ContentTypeEnum implements MyEnum{
	
	HTML(0,"HTML"),
	JSP(1,"JSP"),
	XML(2,"XML");
	
	private final int code;
	private final String desc;
	
	ContentTypeEnum(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	@Override
	public int getCode(){
		return this.code;
	}
	
	@Override
	public String getDesc(){
		return this.desc;
	}
}
