package com.qlp.core.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.util.AssertUtil;
import com.qlp.core.util.CollectionUtil;
import com.qlp.core.util.LogUtil;
import com.qlp.core.util.StringUtil;

public class GeneratorHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneratorHelper.class);
	
	private Generator generator;
	
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
				LogUtil.info(logger, "根据配置的entitys组装待处理Target:{0}",target);
				list.add(target);
			}
		}
		LogUtil.info(logger, "根据配置的entitys组装待处理Target end");
		return list;
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
		Map<String,Target> model = null;
		for (Target target : targets) {
			model = new HashMap<>(2);
			model.put("target", target);
			String basePath = getBasePath(target);
			LogUtil.info(logger, "代码文件存放路径basePath：{0}",basePath);
			createDao(model,basePath);
			createService(model,basePath);
			createController(model,basePath);
		}
		
	}
	
	private void createDao(Map<String, Target> model, String basePath) {
		// TODO Auto-generated method stub
		
	}

	private void createService(Map<String, Target> model, String basePath) {
		// TODO Auto-generated method stub
		
	}

	private void createController(Map<String, Target> model, String basePath) {
		// TODO Auto-generated method stub
		
	}

	private String getBasePath(Target target) {
		StringBuilder sb = new StringBuilder(generator.getSrcPath());
		sb.append(File.separator).append(generator.getParentPath())
		.append(File.separator).append(target.getName());
		return sb.toString();
	}
	
	private void createJsp(List<Target> targets) {
		// TODO Auto-generated method stub
		
	}

	private void createSql(List<Target> targets) {
		// TODO Auto-generated method stub
		
	}

}
