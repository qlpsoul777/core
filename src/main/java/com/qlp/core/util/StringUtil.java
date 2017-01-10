package com.qlp.core.util;

import java.util.StringTokenizer;

/**
 * 字符串工具类
 * ---继承自apache.commons.lang3.StringUtils
 * @author qlp
 *
 */
public class StringUtil{

	/**
	 * 首字母大写
	 * 	如果参数为空或者是空串直接返回<code>null</code>
	 * 	如果首字母是小写，就进行转换，否则原样输出。
	 * @param in 
	 * @return
	 */
	public static String firstCharUpper(String in){
		if(null == in || in.trim().length() == 0){
			return null;
		}
		char c = in.charAt(0);
		if(c >= 'a' || c <= 'z'){
			StringBuilder sb = new StringBuilder(in.length());
			sb.append(Character.toUpperCase(c)).append(in.substring(1));
			return sb.toString();
		}
		return in;
	}
	
	/**
	 * 首字母小写
	 * 	如果参数为空或者是空串直接返回<code>null</code>
	 * 	如果首字母是大写，就进行转换，否则原样输出。
	 * @param in 
	 * @return
	 */
	public static String firstCharLower(String in){
		if(null == in || in.trim().length() == 0){
			return null;
		}
		char c = in.charAt(0);
		if(c >= 'A' || c <= 'Z'){
			StringBuilder sb = new StringBuilder(in.length());
			sb.append(Character.toLowerCase(c)).append(in.substring(1));
			return sb.toString();
		}
		return in;
	}

	public static Long[] toLongArray(String input, String septate) {
		StringTokenizer st = new StringTokenizer(input, septate);
		Long[] result = new Long[st.countTokens()];
		int count = 0;
		while(st.hasMoreElements()){
			result[count++] = DataConvertUtil.toLong(st.nextElement());
		}
		return result;
	}

}
