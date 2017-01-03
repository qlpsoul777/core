package com.qlp.july.core.util;

import java.text.MessageFormat;

import org.slf4j.Logger;

/**
 * 日志输出工具类
 * @author qlp
 * 依赖：    (外部)：slf4j。
 */
public final class LogUtil {
	
	public static void info(Logger logger, String msg) {
		logger.info(msg);
	}
	
	public static void warn(Logger logger, String msg) {
		logger.warn(msg);
	}
	
	public static void error(Logger logger, String msg) {
		logger.error(msg);
	}

	public static void info(Logger logger, String msg, Object... params) {
		logger.info(format(msg,params));
	}
	
	public static void warn(Logger logger, String msg, Object... params) {
		logger.warn(format(msg,params));
	}
	
	public static void error(Logger logger, String msg, Object... params) {
		logger.error(format(msg,params));
	}

	private static String format(String msg, Object... params) {
		if (params != null && params.length != 0) {
			return MessageFormat.format(msg, params);
		}
		return msg;
	}

}
