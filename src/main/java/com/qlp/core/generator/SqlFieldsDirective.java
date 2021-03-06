package com.qlp.core.generator;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map;

import com.qlp.core.exception.MyException;
import com.qlp.core.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SqlFieldsDirective implements TemplateDirectiveModel {
	
	private static final String CLASS_KEY = "className";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		if (params == null || params.size() == 0) {
            throw new MyException("参数不能为空");
        }
		
		String className = ((SimpleScalar) params.get(CLASS_KEY)).getAsString();
		StringBuilder sb = new StringBuilder();
		try {
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields();
			String fieldName;
			for(Field field : fields){
				fieldName = field.getName();
				if(SqlBuildUtil.isBuild(field.getType())){
					sb.append(StringUtil.outLimitSpace(12));
					sb.append(StringUtil.camelToUnderline(fieldName).toUpperCase());
					sb.append(StringUtil.outLimitSpace(1));
					sb.append(fieldName).append(',').append('\n');
				}
			}
		} catch (ClassNotFoundException e) {
			throw new MyException(e);
		}
		Writer out = env.getOut();
		String result = sb.delete(sb.lastIndexOf(","),sb.length()).toString();
		result = result + "\n" + StringUtil.outLimitSpace(6);
		out.write(result);
		out.flush();
	}
	
	

}
