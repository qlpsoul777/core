package com.qlp.core.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import com.qlp.core.util.IoUtil;
import com.qlp.core.util.LogUtil;

/**
 * Web项目工具类
 * @author qlp
 * 依赖： (外部)：javax.servlet、commons.lang3、slf4j、fastjson。
 */
public class WebUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	/**
	 * 从HttpServletResponse获取输出流
	 * @param response
	 * @return
	 */
	public static OutputStream getFromResponse(HttpServletResponse response){
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			LogUtil.error(logger, "从HttpServletResponse获取输出流时出错：{0}", e);
		}
		return os;	
	}
	
	/**
	 * 设置下载响应头
	 * @param request
	 * @param response
	 * @param file
	 */
	public static void setDownloadResponseHeader(HttpServletRequest request,
			HttpServletResponse response, File file) {
		response.setContentType("application/octet-stream; charset=UTF-8");
		
		String fileName = StringUtils.trim(file.getName());
		LogUtil.info(logger, "原始文件名：{0}", fileName);
		try {
			fileName = encodeFileName(request,fileName);
		} catch (UnsupportedEncodingException e) {
			LogUtil.error(logger, "文件名{0}编码时出错：{1}",fileName, e);
		}
		LogUtil.info(logger, "编码后文件名：{0}", fileName);
		response.addHeader("Content-Disposition", "attachment; " + fileName);
		
		response.addHeader("Content-Length", "" + file.length());
	}

	/**
	 * 响应json数据
	 * @param response
	 * @param obj
	 */
	public static void responseJson(HttpServletResponse response, Object obj) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSONObject.toJSONString(obj));
			writer.flush();
		} catch (IOException e) {
			LogUtil.error(logger, "从HttpServletResponse获取PrintWriter时出错：{0}", e);
		}finally{
			IoUtil.close(writer);
		}
	}
	
	/**
	 * 以UTF-8解码指定参数值
	 * @param request
	 * @param key
	 * @return
	 */
	public static String decodeParam(HttpServletRequest request,String key){
		String result = request.getParameter(key);
		if(StringUtils.isNotBlank(result)){
			try {
				result = URLDecoder.decode(result, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				LogUtil.error(logger, "解码{0}时出错：{1}", result,e);
			}
		}
		return result;
	}
	
	/**
	 * 文件名编码
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String encodeFileName(HttpServletRequest request,String fileName) throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
	    if (StringUtils.isEmpty(userAgent)){
	    	return "filename=" + URLEncoder.encode(fileName, "UTF-8");
	    }
	    if (StringUtils.containsIgnoreCase(userAgent, "msie")){
	    	return "filename=" + URLEncoder.encode(fileName, "UTF-8");
	    }
	    if (StringUtils.contains(userAgent, "trident")){
	    	return "filename=" + URLEncoder.encode(fileName, "UTF-8");
	    }
	    if (StringUtils.containsIgnoreCase(userAgent, "opera")) {
	      return "filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8");
	    }
	    return "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
	}

}
