package com.qlp.core.util;


/**
 * 文件名工具类
 * @author qlp
 *
 */
public class FileNameUtil {
	
	public static final String ZIP_SUFFIX = ".zip";	//zip文件后缀
	
	/**
	 * 获取文件名最后一个.的前面部分内容 eg:jdk1.7.45.chm 返回jdk1.7.45
	 * @param originName 不能为blank,否则抛出MyException
	 * @return 如果入参originName包含.号，就返回最后一个.的前面部分内容，否则返回原始originName内容
	 */
	public static String getBeforeName(String originName){
		AssertUtil.assertNotBlank(originName, "文件名不能为blank");
		
		int index0 = originName.lastIndexOf('/');
		int index = originName.lastIndexOf('.');
		if( index0 >= 0){
			if((index0 <index) && (index0 < originName.length())){
				originName = originName.substring(index0+1,index);
			}else{
				originName = originName.substring(index0+1,originName.length());
			}
		}else{
			if(index > 0){
				originName = originName.substring(0,index);
			}
		}
		
		return originName;
	}
	
	/**
	 * 获取文件名的后缀名 eg:jdk1.7.45.chm 返回.chm
	 * @param originName 不能为blank,否则抛出MyException
	 * @return 如果入参originName包含.号，就返回最后一个.的后面部分内容(包含.号)，否则返回空白字符串""
	 */
	public static String getAfterName(String originName){
		AssertUtil.assertNotBlank(originName,"文件名不能为blank");
		
		int index = originName.lastIndexOf('.');
		if( index > 0){
			originName = originName.substring(index,originName.length());
		}else{
			originName = "";
		}
		
		return originName;
	}
	
	public static String buildZipName(){
		return RandomUtil.getLongTimeRandom() + ZIP_SUFFIX;
	}


}
