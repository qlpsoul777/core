package com.qlp.core.enums;

public enum ResponseTypeEnum {
	
	HTML(".html","text/html;charset=utf-8"),
	
	TXT(".txt","text/plain;charset=utf-8"),
	
	JS(".js","text/plain;charset=utf-8"),
	
	CSS(".css","text/plain;charset=utf-8"),
	
	JPG(".jpg","image/jpeg"),
	
	PNG(".png","image/png"),
	
	JPEG(".jpeg","image/jpeg"),
	
	GIF(".gif","image/jpeg"),
	
	DOWNLOAD("other","application/octet-stream;charset=UTF-8");
	
	private final String suffix;
	private final String contentType;
	
	ResponseTypeEnum(String suffix,String contentType){
		this.suffix = suffix;
		this.contentType = contentType;
	}
	
	public String getContentType(){
		return this.contentType;
	}
	
	public static ResponseTypeEnum from(String suffix){
		for (ResponseTypeEnum type : ResponseTypeEnum.values()) {
			if(suffix.equalsIgnoreCase(type.suffix)){
				return type;
			}
		}
		return DOWNLOAD;
	}

}
