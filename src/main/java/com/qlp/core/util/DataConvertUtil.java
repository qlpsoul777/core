package com.qlp.core.util;


/**
 * 数据类型转换工具类
 * @author qlp
 *	依赖： (外部)：无。
 */
public final class DataConvertUtil {
	
	/**
	 * 对象转String
	 * @param obj
	 * @return 如果为null,返回"null",其它返回obj.toString()的结果
	 */
	public static String toStr(Object obj){
		return String.valueOf(obj);
	}
	
	/**
	 * 对象转Long,如果转换失败返回0L。
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj){
		return toLong(obj,0L);
	}
	
	/**
	 * 对象转Long,如果转换失败返回默认值defaultVal。
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static Long toLong(Object obj,Long defaultVal){
		try {
			defaultVal = Long.parseLong(toStr(obj));
		} catch (Exception e) {
			//此处忽略异常处理
		}
		return defaultVal;
	}
	
	/**
	 * 对象转Integer,如果转换失败返回0。
	 * @param obj
	 * @return
	 */
	public static Integer toInt(Object obj){
		return toInt(obj,0);
	}
	
	/**
	 * 对象转Integer,如果转换失败返回默认值defaultVal。
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static Integer toInt(Object obj,Integer defaultVal){
		try {
			defaultVal = Integer.parseInt(toStr(obj));
		} catch (Exception e) {
			//此处忽略异常处理
		}
		return defaultVal;
	}
	
	/**
	 * 对象转Double,如果转换失败返回0.00。
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj){
		return toDouble(obj,0.00);
	}

	/**
	 * 对象转Double,如果转换失败返回默认值defaultVal。
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static Double toDouble(Object obj, double defaultVal) {
		try {
			defaultVal = Double.parseDouble(toStr(obj));
		} catch (Exception e) {
			//此处忽略异常处理
		}
		return defaultVal;
	}
	
}
