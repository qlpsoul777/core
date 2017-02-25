package com.qlp.core.generator;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.util.AssertUtil;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.FreeMarkerUtil;
import com.qlp.core.util.LogUtil;
import com.qlp.core.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GeneratorHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneratorHelper.class);
	
	private Generator generator;
	private static Configuration configuration;
	
	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public void init(){
		LogUtil.info(logger, "generator:{0}",generator);
		if(generator.getIsCreate()){
			LogUtil.info(logger, "-------自动生成文件start-------");
			
			initConfiguration();
			
			List<Target> targets = getTargets();
			if(CollectionUtil.isNotBlank(targets)){
				createCode(targets);
				
				createSql(targets);
				
				createJsp(targets);
			}
			
			LogUtil.info(logger, "-------自动生成文件end-------");
		}
	}

	public void destory(){
		this.generator = null;
	}
	
	/**
	 * 根据配置的entitys组装待处理Target
	 * @return
	 */
	private List<Target> getTargets() {
		List<Target> list = null;
		List<String> entitys = generator.getEntitys();
		if(CollectionUtil.isNotBlank(entitys)){
			LogUtil.info(logger, "根据配置的entitys组装待处理Target start");
			String rootPath = generator.getRootPath();
			String appName = getByRootPath(rootPath);
			list = new ArrayList<>(entitys.size());
			Target target = null;
			for(String name : entitys){
				target = new Target(rootPath, appName);
				target.setParentPath(generator.getParentPath());
				target.setName(name);
				target.setLowName(StringUtil.firstCharLower(name));
				target.setTableName(getTableName(generator.getParentPath(),name,appName));
				target.setClassName(getClassName(rootPath,generator.getParentPath(),name));
				LogUtil.info(logger, "根据配置的entitys组装待处理Target:{0}",target);
				list.add(target);
			}
		}
		LogUtil.info(logger, "根据配置的entitys组装待处理Target end");
		return list;
	}
	
	private String getClassName(String rootPath, String parentPath, String name) {
		StringBuilder sb = new StringBuilder(rootPath);
		sb.append(".").append("entity.").append(parentPath)
		.append(".").append(name);
		return sb.toString();
	}

	private String getTableName(String parentPath, String name,String appName) {
		StringBuilder sb = new StringBuilder("T_");
		sb.append(appName.toUpperCase()).append("_")
		.append(parentPath.toUpperCase()).append("_")
		.append(name.toUpperCase());
		return sb.toString();
	}

	/**
	 * 根据rootPath获取appName
	 * @return
	 */
	private String getByRootPath(String rootPath) {
		AssertUtil.assertNotBlank(rootPath, "工程包路径不能为null");
		String appName = rootPath.substring(rootPath.lastIndexOf(".") + 1);
		LogUtil.info(logger, "appName:{0}",appName);
		return appName;
	}
	
	/**
	 * 生成代码文件
	 * @param targets
	 */
	private void createCode(List<Target> targets) {
		LogUtil.info(logger, "-------自动生成java代码文件start-------");
		Map<String,Target> model = null;
		for (Target target : targets) {
			model = new HashMap<>(2);
			model.put("target", target);
			createDao(model,getPath(target,"dao"));
			createService(model,getPath(target,"service"));
			createController(model,getPath(target,"controller"));
		}
		LogUtil.info(logger, "-------自动生成java代码文件end-------");
	}
	
	private static void initConfiguration() {
		URL url = GeneratorHelper.class.getResource("/template");
		String path = url.getFile();
		LogUtil.info(logger, "模板文件存放文件夹地址：{0}", path);
		AssertUtil.assertNotBlank(path, "template文件不存在");
		configuration = FreeMarkerUtil.getConfiguration(path);
	}
	
	private boolean exists(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * 生成Dao层代码文件
	 * @param model
	 * @param basePath
	 */
	private void createDao(Map<String, Target> model, String path) {
		if(!exists(path)){
			Template template = FreeMarkerUtil.getTemplate(configuration,"BaseDao.ftl");
			AssertUtil.assertNotNull(template, "template is null");
			
			FreeMarkerUtil.renderFile(template, model, path);
		}else{
			LogUtil.info(logger, "文件：{0}，已存在，跳过本次操作",path);
		}
	}

	

	/**
	 * 生成Service层代码文件
	 * @param model
	 * @param basePath
	 */
	private void createService(Map<String, Target> model, String path) {
		if(!exists(path)){
			Template template = FreeMarkerUtil.getTemplate(configuration,"BaseService.ftl");
			AssertUtil.assertNotNull(template, "template is null");
			
			FreeMarkerUtil.renderFile(template, model, path);
		}else{
			LogUtil.info(logger, "文件：{0}，已存在，跳过本次操作",path);
		}
		
	}

	/**
	 * 生成Controller层代码文件
	 * @param model
	 * @param basePath
	 */
	private void createController(Map<String, Target> model, String path) {
		if(!exists(path)){
			Template template = FreeMarkerUtil.getTemplate(configuration,"BaseController.ftl");
			AssertUtil.assertNotNull(template, "template is null");
			
			FreeMarkerUtil.renderFile(template, model, path);
		}else{
			LogUtil.info(logger, "文件：{0}，已存在，跳过本次操作",path);
		}
	}

	/**
	 * 获取生成文件的基本绝对路径
	 * @param target
	 * @return
	 */
	private String getPath(Target target,String type) {
		StringBuilder sb = new StringBuilder(generator.getSrcPath());
		sb.append(File.separator).append(type)
		.append(File.separator).append(generator.getParentPath())
		.append(File.separator).append(target.getName())
		.append(StringUtil.firstCharUpper(type)).append(".java");
		return sb.toString();
	}
	
	private void createJsp(List<Target> targets) {
		if(generator.getIsCreateJsp()){
			LogUtil.info(logger, "-------自动生成jsp文件start-------");
			LogUtil.info(logger, "-------自动生成jsp文件end-------");
		}
	}

	private void createSql(List<Target> targets) {
		if(generator.getIsCreateSql()){
			LogUtil.info(logger, "-------自动生成sql文件start-------");
			Map<String,Object> model = null;
			for (Target target : targets) {
				model = new HashMap<>(2);
				model.put("target", target);
				model.put("sql_fields", new SqlFieldsDirective());
				model.put("sql_case", new SqlCaseDirective());
				createSqlFile(model, getSqlPath(target));
			}
			LogUtil.info(logger, "-------自动生成sql文件end-------");
		}
		
	}

	private void createSqlFile(Map<String, Object> model, String path) {
		if(!exists(path)){
			Template template = FreeMarkerUtil.getTemplate(configuration,"BaseSql.ftl");
			AssertUtil.assertNotNull(template, "template is null");
			
			FreeMarkerUtil.renderFile(template, model, path);
		}else{
			LogUtil.info(logger, "文件：{0}，已存在，跳过本次操作",path);
		}
		
	}

	private String getSqlPath(Target target) {
		StringBuilder sb = new StringBuilder(generator.getSqlPath());
		sb.append(File.separator);
		sb.append(target.getLowName()).append(".xml");
		return sb.toString();
	}

}
