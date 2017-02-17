package com.qlp.core.util;

import java.util.StringTokenizer;

/**
 * 字符串工具类
 * ---继承自apache.commons.lang3.StringUtils
 * @author qlp
 *
 */
public class StringUtil{
	
	public static final char UNDERLINE='_';

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
		if(Character.isLowerCase(c)){
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
		if(Character.isUpperCase(c)){
			StringBuilder sb = new StringBuilder(in.length());
			sb.append(Character.toLowerCase(c)).append(in.substring(1));
			return sb.toString();
		}
		return in;
	}
	
	/**
	 * 驼峰式改为下划线分割式eg:userName--->user_name
	 * @param in
	 * @return
	 */
	public static String camelToUnderline(String in){
		if(null == in || in.trim().length() == 0){
			return null;
		}
		if(in.length() < 4){
			return in;
		}
		char c;
		int len = in.length();
		StringBuilder sb = new StringBuilder(len + 4);
		for(int i=0;i<len;i++){
			c = in.charAt(i);
			if(Character.isUpperCase(c)){
				sb.append(UNDERLINE).append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 *  下划线分割式改为驼峰式eg:user_name--->userName
	 * @param in
	 * @return
	 */
	public static String underlineToCamel(String in) {
		if (null == in || in.trim().length() == 0) {
			return null;
		}
		int len = in.length();
		StringBuilder sb = new StringBuilder(len);
		char c;
		for (int i = 0; i < len; i++) {
			c = in.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(in.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 输出指定长度的空格，一般用于字符串格式化
	 * @param limit 长度范围是1-127
	 * @return
	 */
	public static String outLimitSpace(int limit){
		if(limit <=0 || limit > Byte.MAX_VALUE){
			throw new IllegalArgumentException("长度超出限制");
		}
		StringBuilder sb = new StringBuilder(limit);
		for (int i = 0; i < limit; i++) {
			sb.append(" ");
		}
		return sb.toString();
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
