package com.qlp.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射工具类---摘自spring core包
 * 
 * @author qlp
 * 
 */
public class ReflectionUtil {

	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while ((!Object.class.equals(searchType)) && (searchType != null)) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if (((name == null) || (name.equals(field.getName())))
						&& ((type == null) || (type.equals(field.getType())))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			throw new IllegalStateException(
					"Unexpected reflection exception - "
							+ ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		} catch (IllegalAccessException ex) {
			throw new IllegalStateException(
					"Unexpected reflection exception - "
							+ ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	public static void makeAccessible(Field field) {
		if (((!Modifier.isPublic(field.getModifiers()))
				|| (!Modifier
						.isPublic(field.getDeclaringClass().getModifiers())) || (Modifier
					.isFinal(field.getModifiers()))) && (!field.isAccessible())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 获取指定对象指定属性名的值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Class<?> clazz = obj.getClass();
		Field field = findField(clazz, fieldName);
		makeAccessible(field);
		return getField(field, obj);
	}

	/**
	 * 将指定对象的指定属性的值设置为指定值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param fieldValue
	 */
	public static void setFieldValue(Object obj, String fieldName,
			Object fieldValue) {
		Class<?> clazz = obj.getClass();
		Field field = findField(clazz, fieldName);
		makeAccessible(field);
		setField(field, obj, fieldValue);
	}
	
}
