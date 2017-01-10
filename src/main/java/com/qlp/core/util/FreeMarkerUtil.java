package com.qlp.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import com.qlp.core.exception.MyException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {
	
	public static String renderString(String templateStr,Map<String,Object> model) throws Exception{
		StringWriter result = new StringWriter();
		Template t = new Template("default", new StringReader(templateStr), new Configuration());
		t.process(model, result);
		return result.toString();
	}
	
	public static void renderFile(Template template,Map<String,?> model,String path){
		File file = FileUtil.createParentIfNotExists(new File(path));
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			template.process(model, fw);
		} catch (Exception e) {
			throw new MyException(e);
		}finally{
			IoUtil.close(fw);
		}
	}
	
	public static Template getTemplate(Configuration config,String templateName){
		Template template = null;
		try {
			template  = config.getTemplate(templateName);
		} catch (IOException e) {
			throw new MyException(e);
		}
		return template;
	}

}
