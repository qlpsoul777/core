package com.qlp.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import com.qlp.core.exception.MyException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerUtil {
	
	public static Configuration getConfiguration(String path){
		Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.SIMPLIFIED_CHINESE);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        File templateFiles = new File(path);
        try {
            cfg.setDirectoryForTemplateLoading(templateFiles);
        } catch (IOException e) {
            throw new MyException(e);
        }
        return cfg;
    }
	
	public static String renderString(String templateStr,Map<String,Object> model) throws Exception{
		StringWriter result = new StringWriter();
		Template t = new Template("default", new StringReader(templateStr), new Configuration());
		t.process(model, result);
		return result.toString();
	}
	
	public static void renderFile(Template template,Map<String,?> model,String path){
		File file = FileUtil.createParentIfNotExists(new File(path));
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			template.process(model, out);
		} catch (Exception e) {
			throw new MyException(e);
		}finally{
			IoUtil.close(out);
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
