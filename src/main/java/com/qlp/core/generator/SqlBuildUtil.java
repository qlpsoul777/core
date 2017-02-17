package com.qlp.core.generator;

import java.util.Date;

import com.qlp.core.util.StringUtil;

public final class SqlBuildUtil {
	
	public static boolean isBuild(Class<?> clazz) {
		String typeName = clazz.getName();
		if(typeName.startsWith("java.lang")){
			return true;
		}
		if(typeName.equals("java.util.Date")){
			return true;
		}
		if(clazz.isEnum()){
			return true;
		}
		return false;
	}

	public static boolean isString(Class<?> type) {
		return String.class == type;
	}

	public static String getJdbcType(Class<?> clazz) {
		if(isString(clazz) || clazz.isEnum()){
			return "VARCHAR";
		}
		if(isDate(clazz)){
			return "TIMESTAMP";
		}
		return "NUMERIC";
	}

	private static boolean isDate(Class<?> clazz) {
		return Date.class == clazz;
	}

	public static String getIfCondition(String fieldName, Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		if(isString(clazz)){
			sb.append("<if test=\"").append(fieldName).append(" != null and ");
			sb.append(fieldName).append(" !=''\">\n");
		}else{
			sb.append("<if test=\"").append(fieldName).append(" != null\">\n");
		}
		sb.append(StringUtil.outLimitSpace(16));
		return sb.toString();
	}

}
