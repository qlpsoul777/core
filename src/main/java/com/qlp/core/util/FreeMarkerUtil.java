package com.qlp.core.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {
	
	public static String renderString(String templateStr,Map<String,Object> model) throws Exception{
		StringWriter result = new StringWriter();
		Template t = new Template("default", new StringReader(templateStr), new Configuration());
		t.process(model, result);
		return result.toString();
	}
	
	public static void renderFile(Template template,String path){
		
	}

}
