package com.qlp.core.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO工具类
 * @author qlp
 * 依赖：    (外部)：slf4j。
 */
public class IoUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(IoUtil.class);

	/**
	 * 关闭单个Closeable
	 * @param closeable
	 */
	public static void close(Closeable closeable){
		if(closeable != null){
			try {
				closeable.close();
			} catch (IOException e) {
				LogUtil.error(logger, "关闭流出错：{1}",e);
			}
		}
	}
	
	/**
	 * 关闭多个Closeable
	 * @param closeables
	 */
	public static void close(Closeable ... closeables){
		if (closeables != null) {
			try {
				for (Closeable closeable : closeables) {
	                if (closeable != null) {
	                    closeable.close();
	                }
	            }
			} catch (Exception e) {
				LogUtil.error(logger, "关闭流出错：{1}",e);
			}
        }
	}
	
	public static void close(AutoCloseable ... autos){
		if (autos != null) {
			try {
				for (AutoCloseable closeable : autos) {
					if (closeable != null) {
						closeable.close();
					}
				}
			} catch (Exception e) {
				LogUtil.error(logger, "关闭流出错：{1}",e);
			}
		}
	}

}
