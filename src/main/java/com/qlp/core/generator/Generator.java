package com.qlp.core.generator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.entity.BaseEntity;
import com.qlp.core.exception.MyException;
import com.qlp.core.util.AssertUtil;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.FreeMarkerUtil;
import com.qlp.core.util.LogUtil;
import com.qlp.core.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class Generator extends BaseEntity{
	
	private static final long serialVersionUID = 5234310636239414609L;

	private static final Logger logger = LoggerFactory.getLogger(Generator.class);
	
	private boolean isCreate;		//是否生成代码文件 
	private boolean isCreateSql;	//是否生成sql(mapper.xml)文件
	private boolean isCreateJsp;	//是否生成jsp文件
	
	private String srcPath;			//工程源代码绝对根路径
	private String rootPath;		//工程包路径
	private String parentPath;		//实体所在父路径
	private String sqlPath;			//sql配置文件存放路径
	private String jspPath;			//jsp文件存放路径
	
	private List<String> entitys;	//实体名集合
	
	
	
	private String getBasePath(Target target) {
		StringBuilder sb = new StringBuilder(srcPath);
		sb.append(File.separator).append(parentPath)
		.append(File.separator).append(target.getName());
		return sb.toString();
	}

	private void createController(Map<String,Target> model,String basePath) {
		Template template = FreeMarkerUtil.getTemplate(FreemarkConfiguration.Instance.getConfiguration(),"BaseDao.ftl");
		AssertUtil.assertNotNull(template, "template is null");
		
		FreeMarkerUtil.renderFile(template, model, basePath + "Controller.java");
		
	}

	private void createService(Map<String,Target> model,String basePath) {
		Template template = FreeMarkerUtil.getTemplate(FreemarkConfiguration.Instance.getConfiguration(),"BaseDao.ftl");
		AssertUtil.assertNotNull(template, "template is null");
		
		FreeMarkerUtil.renderFile(template, model, basePath + "Service.java");
		
	}

	private void createDao(Map<String,Target> model,String basePath) {
		Template template = FreeMarkerUtil.getTemplate(FreemarkConfiguration.Instance.getConfiguration(),"BaseDao.ftl");
		AssertUtil.assertNotNull(template, "template is null");
		
		FreeMarkerUtil.renderFile(template, model, basePath + "Dao.java");
	}

	private List<Target> getTargets() {
		List<Target> list = null;
		if(CollectionUtil.isNotBlank(entitys)){
			String appName = getByRootPath();
			list = new ArrayList<>(entitys.size());
			Target target = null;
			for(String name : entitys){
				target = new Target(rootPath, appName);
				target.setParentPath(parentPath);
				target.setName(name);
				target.setLowName(StringUtil.firstCharLower(name));
				list.add(target);
			}
		}
		return list;
	}

	
	/**
	 * 根据rootPath获取appName
	 * @return
	 */
	private String getByRootPath() {
		AssertUtil.assertNotBlank(rootPath, "工程包路径不能为null");
		String appName = rootPath.substring(rootPath.lastIndexOf(".") + 1);
		LogUtil.info(logger, "appName:{0}",appName);
		return appName;
	}

	public Generator(){}
	
	public boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(boolean isCreate) {
		this.isCreate = isCreate;
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

	public List<String> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<String> entitys) {
		this.entitys = entitys;
	}

	public boolean getIsCreateSql() {
		return isCreateSql;
	}

	public void setIsCreateSql(boolean isCreateSql) {
		this.isCreateSql = isCreateSql;
	}

	public String getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}

	public boolean getIsCreateJsp() {
		return isCreateJsp;
	}

	public void setIsCreateJsp(boolean isCreateJsp) {
		this.isCreateJsp = isCreateJsp;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}



	private enum FreemarkConfiguration{
		Instance;
		
		public Configuration getConfiguration() {
	        Configuration cfg = new Configuration();
	        cfg.setDefaultEncoding("UTF-8");
	        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	        
	        URL url = FreemarkConfiguration.class.getResource("/template");
	        String path = url.getFile();
	        AssertUtil.assertNotBlank(path, "template文件不存在");
	        
	        File templateFiles = new File(path);
	        try {
	            cfg.setDirectoryForTemplateLoading(templateFiles);
	        } catch (IOException e) {
	            throw new MyException(e);
	        }
	        return cfg;
	    }
	}
	
}
