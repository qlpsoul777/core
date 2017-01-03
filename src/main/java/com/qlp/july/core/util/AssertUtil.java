package com.qlp.july.core.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.qlp.july.core.exception.ErrorDetail;
import com.qlp.july.core.exception.MyException;

/**
 * 断言工具类
 * @author qlp
 *	依赖： (外部)：commons.lang3。
 */
public final class AssertUtil {
	
	/**
	 * 判断对象不为null,否则抛出MyException
	 * @param obj
	 * @param msg
	 */
	public static void assertNotNull(Object obj, String msg) {
		if(null == obj){
			throw new MyException(msg);
		}
	}
	
	/**
	 * 判断对象不为null,否则抛出MyException
	 * @param obj
	 * @param detail
	 */
	public static void assertNotNull(Object obj, ErrorDetail detail) {
		if(null == obj){
			throw new MyException(detail);
		}
	}
	
	/**
	 * 判断对象不为null,否则抛出MyException
	 * @param o
	 * @param detail
	 * @param msg
	 */
	public static void assertNotNull(Object o,ErrorDetail detail,String msg){
		if(null == o){
			throw new MyException(detail, msg);
		}
	}
	
	/**
	 * 判断集合不为blank,否则抛出MyException
	 * @param collection
	 * @param msg
	 */
	public static void assertNotBlank(Collection<?> collection,String msg){
		if(CollectionUtil.isBlank(collection)){
			throw new MyException(msg);
		}
	}
	
	/**
	 * 判断map不为blank,否则抛出MyException
	 * @param map
	 * @param detail
	 * @param msg
	 */
	public static void assertNotBlank(Map<?,?> map,ErrorDetail detail,String msg){
		if(CollectionUtil.isBlank(map)){
			throw new MyException(detail, msg);
		}
	}
	
	/**
	 * 判断字符串不为blank,否则抛出MyException
	 * @param str
	 * @param msg
	 */
	public static void assertNotBlank(String str,String msg){
		if(StringUtils.isBlank(str)){
			throw new MyException(msg);
		}
	}
	
	/**
	 * 判断字符串不为blank,否则抛出MyException
	 * @param str
	 * @param detail
	 * @param msg
	 */
	public static void assertNotBlank(String str,ErrorDetail detail,String msg){
		if(StringUtils.isBlank(str)){
			throw new MyException(detail, msg);
		}
	}

	/**
	 * 判断结果为true,否则抛出MyException
	 * @param result
	 * @param msg
	 */
	public static void assertTrue(boolean result,String msg){
		if(!result){
			throw new MyException(msg);
		}
	}

	/**
	 * 判断结果为true,否则抛出MyException
	 * @param result
	 * @param detail
	 * @param msg
	 */
	public static void assertTrue(boolean result,ErrorDetail detail,String msg){
		if(!result){
			throw new MyException(detail, msg);
		}
	}

	

}
