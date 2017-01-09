package com.qlp.core.generator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.util.AssertUtil;
import com.qlp.core.util.LogUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class Generator {
	
	private static final Logger logger = LoggerFactory.getLogger(Generator.class);
	
	private String srcPath;
	private String rootPath;
	private List<String> entitys;
	
	public void init(){
		LogUtil.info(logger, "-------自动生成代码start-------");
			
			
		//获取对应template
		
	}
	
	public Generator(){}

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
	
	public List<String> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<String> entitys) {
		this.entitys = entitys;
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
	            e.printStackTrace();
	        }
	        return cfg;
	    }
	}
	
}
