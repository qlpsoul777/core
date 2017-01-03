package com.qlp.july.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间工具类
 * @author qlp
 * 依赖： (外部)：slf4j。
 */
public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static enum FormatDate{
		
		/** 格式：yyyy-MM-dd */
		YMD_1("yyyy-MM-dd"),
		
		/** 格式：yyyyMMddHHmmssSSS */
		YMDHMSS("yyyyMMddHHmmssSSS");
		
		private final String pattern; 
		
		private FormatDate(String pattern){
			this.pattern = pattern;
		}

		public DateFormat getFormat(){
			return new SimpleDateFormat(this.pattern);
		}
	}
	
	/**
	 * 根据指定格式将日期时间转成字符串
	 * @param date 不能为null,否则抛出MyException
	 * @param format 不能为null,否则抛出MyException
	 * @return
	 */
	public static String date2String(Date date,FormatDate format){
		AssertUtil.assertNotNull(date, "Date参数不能为null");
		AssertUtil.assertNotNull(format, "FormatDate参数不能为null");
		return format.getFormat().format(date);
	}
	
	/**
	 * 根据指定格式将字符串转日期时间,如果转换出错返回null
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date string2Date(String dateStr,FormatDate format){
		try {
			return format.getFormat().parse(dateStr);
		} catch (ParseException e) {
			LogUtil.error(logger, "字符串{0}转日期时间出错：{1}", dateStr,e);
		}
		return null;
	}

	/**
	 * 时间戳值转字符串
	 * @param times
	 * @param format
	 * @return
	 */
	public static String stamp2DateStr(long times,FormatDate format){
		Date date =new Date(times);
		return date2String(date,format);
	}

}
