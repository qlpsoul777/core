package com.qlp.core.enums;


/**
 * 状态枚举
 * @author qlp
 *
 */
public enum StatusEnum{
	
	DISABLE(0,"禁用"),ENABLE(1,"启用");
	
	private final int code;
	private final String desc;
	
	private StatusEnum(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getDesc(){
		return desc;
	}

	public static StatusEnum from(Integer code) {
		for (StatusEnum status : StatusEnum.values()) {
			if(status.getCode() == code){
				return status;
			}
		}
		return null;
	}
}
