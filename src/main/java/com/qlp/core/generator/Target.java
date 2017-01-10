package com.qlp.core.generator;

import com.qlp.core.entity.BaseEntity;

public class Target extends BaseEntity{
	
	private static final long serialVersionUID = -1000824977279287248L;
	
	private  final String rootPath;	//工程绝对根路径
	private  final String appName;	//工程名
	
	private String parentPath;	//父目录路径
	private String name;		//对象名
	private String lowName;		//首字母小写对象名
	
	public Target(String rootPath,String appName){
		this.rootPath = rootPath;
		this.appName = appName;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLowName() {
		return lowName;
	}

	public void setLowName(String lowName) {
		this.lowName = lowName;
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public String getAppName() {
		return appName;
	}
	
	
}
