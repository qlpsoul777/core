package com.qlp.core.enums;

public enum EnvironmentEnum {
	
	DEVELOPMENT("development","开发环境"),
	TEST("test","测试环境"),
	DEPLOY("deploy","部署环境");
	
	private final String code;
	private final String desc;
	
	private EnvironmentEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getDesc(){
		return desc;
	}
}
