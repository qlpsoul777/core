package com.qlp.core.generator;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map;

import com.qlp.core.exception.MyException;
import com.qlp.core.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SqlCaseDirective implements TemplateDirectiveModel {
	
	private static final String CLASS_KEY = "className";
	private static final String TYPE_KEY = "type";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		if (params == null || params.size() == 0) {
            throw new MyException("参数不能为空");
        }
		
		String className = ((SimpleScalar) params.get(CLASS_KEY)).getAsString();
		int type = 	((SimpleNumber) params.get(TYPE_KEY)).getAsNumber().intValue();
		
		String result = null;
		switch(type){
		case 1:
			result = getInsertFields(className);
			break;
		case 2:
			result = getInsertValues(className);
			break;
		case 3:
			result = getUpdateSets(className);
			break;
		}
		
		Writer out = env.getOut();
		out.write(result);
		out.flush();
	}

	private String getUpdateSets(String className) {
		StringBuilder sb = new StringBuilder();
		try {
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields();
			String fieldName;
			for (Field field : fields) {
				fieldName = field.getName();
				clazz = field.getType();
				if(SqlBuildUtil.isBuild(clazz)){
					sb.append(StringUtil.outLimitSpace(12));
					sb.append(SqlBuildUtil.getIfCondition(fieldName,clazz));
					sb.append(StringUtil.camelToUnderline(fieldName).toUpperCase());
					sb.append(" = #{").append(fieldName).append(",jdbcType=");
					sb.append(SqlBuildUtil.getJdbcType(clazz));
					sb.append("},").append("\n");
					sb.append(StringUtil.outLimitSpace(12)).append("</if>\r\n");
				}
			}
		} catch (Exception e) {
			throw new MyException(e);
		}
		return sb.toString();
	}

	private String getInsertValues(String className) {
		StringBuilder sb = new StringBuilder();
		try {
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields();
			String fieldName;
			for (Field field : fields) {
				fieldName = field.getName();
				clazz = field.getType();
				if(SqlBuildUtil.isBuild(clazz)){
					sb.append(StringUtil.outLimitSpace(12));
					sb.append(SqlBuildUtil.getIfCondition(fieldName,clazz));
					sb.append("#{").append(fieldName).append(",jdbcType=");
					sb.append(SqlBuildUtil.getJdbcType(clazz));
					sb.append("},").append("\n");
					sb.append(StringUtil.outLimitSpace(12)).append("</if>\r\n");
				}
			}
		} catch (Exception e) {
			throw new MyException(e);
		}
		return sb.toString();
	}

	private String getInsertFields(String className) {
		StringBuilder sb = new StringBuilder();
		try {
			Class<?> clazz = Class.forName(className);
			Field[] fields = clazz.getDeclaredFields();
			String fieldName;
			for (Field field : fields) {
				fieldName = field.getName();
				clazz = field.getType();
				if(SqlBuildUtil.isBuild(clazz)){
					sb.append(StringUtil.outLimitSpace(12));
					sb.append(SqlBuildUtil.getIfCondition(fieldName,clazz));
					sb.append(StringUtil.camelToUnderline(fieldName).toUpperCase()).append(",");
					sb.append("\n").append(StringUtil.outLimitSpace(12)).append("</if>\r\n");
				}
			}
		} catch (Exception e) {
			throw new MyException(e);
		}
		return sb.toString();
	}
}
