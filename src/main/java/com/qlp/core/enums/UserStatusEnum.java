package com.qlp.core.enums;

public enum UserStatusEnum {
	
	UN_ACTIVED(-1,"未激活"),
	DISABLE(0,"禁用"),
	ENABLE(1,"启用"),
	LOCKED(2,"已锁定");
	
	private final int code;
	private final String desc;
	
	private UserStatusEnum(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getDesc(){
		return desc;
	}

}
