package com.qlp.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * @author qlp
 *  依赖： (外部)：无。
 */
public final class CollectionUtil {
	
	/**
	 * 判断集合是null
	 * @param col
	 * @return
	 */
	public static boolean isNull(Collection<?> col){
		return null == col;
	}
	
	/**
	 * 判断集合不是null
	 * @param col
	 * @return
	 */
	public static boolean isNotNull(Collection<?> col){
		return !isNull(col);
	}
	
	/**
	 * 判断集合是blank
	 * @param col
	 * @return
	 */
	public static boolean isBlank(Collection<?> col){
		return (null== col) || (col.isEmpty());
	}
	
	/**
	 * 判断集合不是blank
	 * @param col
	 * @return
	 */
	public static boolean isNotBlank(Collection<?> col){
		return !isBlank(col);
	}
	
	/**
	 * 判断Map是否为null
	 * @param col
	 * @return
	 */
	public static boolean isNull(Map<?,?> map){
		return null== map;
	}
	
	/**
	 * 判断Map为blank
	 * @param map
	 * @return
	 */
	public static boolean isBlank(Map<?,?> map){
		return (null== map) || (map.isEmpty());
	}
	
	/**
	 * 判断Map不为blank
	 * @param map
	 * @return
	 */
	public static boolean isNotBlank(Map<?,?> map){
		return (null!= map) && (!map.isEmpty());
	}

}
