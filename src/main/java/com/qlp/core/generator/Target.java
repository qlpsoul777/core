package com.qlp.core.generator;

public class Target {
	
	private  final String rootPath;
	private  final String appName;
	
	private String parentPath;
	private String name;
	private String lowName;
	
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
