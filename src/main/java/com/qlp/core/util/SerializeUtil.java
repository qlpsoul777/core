package com.qlp.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.exception.MyException;

public class SerializeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		
		if (object == null) {
			return new byte[0];
		}
		
		byte[] result = null;
		try {
			if (!(object instanceof Serializable)) {
				throw new MyException("传入对象未实现序列化接口：" + object.getClass().getName());
			}
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			result =  byteStream.toByteArray();	
		} catch (Exception e) {
			LogUtil.error(logger, "序列化{0}，失败：{1}",object,e);
		}
		return result;
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		
		if (CollectionUtil.isBlank(bytes)) {
			return null;
		}

		Object result = null;
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
			result = objectInputStream.readObject();
		} catch (Exception e) {
			LogUtil.error(logger, "反序列化{0}，失败：{1}",bytes,e);
		}
		return result;
	}

}
