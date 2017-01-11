package com.qlp.core.generator;

import java.util.List;

import com.qlp.core.entity.BaseEntity;

public class Generator extends BaseEntity{
	
	private static final long serialVersionUID = 5234310636239414609L;

	private boolean isCreate;		//是否生成代码文件 
	private boolean isCreateSql;	//是否生成sql(mapper.xml)文件
	private boolean isCreateJsp;	//是否生成jsp文件
	
	private String srcPath;			//工程源代码绝对根路径
	private String rootPath;		//工程包路径
	private String parentPath;		//实体所在父路径
	private String sqlPath;			//sql配置文件存放路径
	private String jspPath;			//jsp文件存放路径
	
	private List<String> entitys;	//实体名集合

	public Generator(){}
	
	public boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}
	
	public boolean getIsCreateSql() {
		return isCreateSql;
	}

	public void setIsCreateSql(boolean isCreateSql) {
		this.isCreateSql = isCreateSql;
	}
	
	public boolean getIsCreateJsp() {
		return isCreateJsp;
	}

	public void setIsCreateJsp(boolean isCreateJsp) {
		this.isCreateJsp = isCreateJsp;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
	public String getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public List<String> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<String> entitys) {
		this.entitys = entitys;
	}
	
}
