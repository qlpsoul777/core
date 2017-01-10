package com.qlp.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlp.core.exception.ErrorDetail.BusiErrorEnum;
import com.qlp.core.exception.MyException;

/**
 * 文件操作工具类
 */
public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static final boolean DEFAULT_ISDELETE = Boolean.FALSE;
	
	/**
	 * 输入流写入文件
	 * @param ins
	 * @param file
	 */
	public static void writeInFile(InputStream ins, File file) {
		AssertUtil.assertNotNull(file,"文件不能为null");
		
		OutputStream os = null;
		int count;
		byte[] buffer = new byte[1024];
		try {
			os = new BufferedOutputStream(new FileOutputStream(file));
			while ((count = ins.read(buffer)) != -1) {
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			LogUtil.error(logger, "写入文件{0}出错：{1}", file.getAbsolutePath(),e);
		}finally{
			IoUtil.close(ins,os);
		}
	}

	/**
	 *	将文件用指定输出流输出
	 * @param os 输出流
	 * @param file 文件,不能是文件夹或null
	 */
	public static void writeOutFile(OutputStream fromResponse, File file) {
		writeOutFile(fromResponse,file,DEFAULT_ISDELETE);
	}

	/**
	 *	将文件用指定输出流输出，并根据isDelete决定输出后是否删除原文件true：删除；false：不删除
	 * @param os 输出流
	 * @param file 文件,不能是文件夹或null
	 * @param isDelete 输出后是否删除原文件 
	 */
	public static void writeOutFile(OutputStream os, File file,boolean isDelete) {
		
		AssertUtil.assertNotNull(file,"文件不能为null");
		AssertUtil.assertTrue(file.exists() && file.isFile(),"文件不存在/文件不能是文件夹");
		
		InputStream fis = null;
		int count;
		byte[] buffer = new byte[1024];
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			while ((count = fis.read(buffer)) != -1){
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			LogUtil.error(logger, "写出文件{0}出错：{1}", file.getAbsolutePath(),e);
		}finally{
			
			IoUtil.close(fis,os);
			
			if(isDelete){
				deleteAllFiles(file);
			}
		}
	}

	/**
	 * 删除指定文件或文件夹
	 * @param file 文件路径
	 */
	public static void deleteAllFiles(File file) {
		AssertUtil.assertNotNull(file,"文件不能为null");
		
		if (!file.exists()){
			return;
		}
		
		if (file.isFile()) {
			file.delete();
			return;
		}
		
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFiles(files[i]);
		}
	}

	
	/**
	 * 压缩文件或文件夹
	 * @param sourcePath 待压缩文件全路径
	 * @param targetPath 输出压缩文件全路径
	 * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
	 */
	public static void compress(String sourcePath,String targetPath,boolean dirFlag){
		
		AssertUtil.assertNotNull(sourcePath, BusiErrorEnum.INPUT_NOT_EXIST, "源文件路径不能为null");
		AssertUtil.assertNotNull(targetPath, BusiErrorEnum.INPUT_NOT_EXIST, "目标文件路径不能为null");
		
		compress(new File(sourcePath),new File(targetPath),dirFlag);
	}

	/**
	 * 压缩文件或文件夹
	 * @param sourceFile 待压缩文件
	 * @param targetFile 输出压缩文件
	 * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
	 */
	public static void compress(File sourceFile,File targetFile,boolean dirFlag){
		
		AssertUtil.assertNotNull(sourceFile,"文件不能为null");
		AssertUtil.assertTrue(sourceFile.exists(),"文件不存在");
		
		//判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
        if (sourceFile.isDirectory() && (StringUtils.indexOf(targetFile.getPath(), sourceFile.getPath()) != -1)) {
        	throw new MyException("压缩文件保存的路径不能是源文件路径的子路径");
        }
        
        //判断压缩文件保存的路径是否存在，如果不存在，则创建
        createParentIfNotExists(targetFile);
        
        ZipOutputStream os = null;
        try {
			os = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(targetFile), new CRC32()));
			zip(os,sourceFile,dirFlag);
		} catch (FileNotFoundException e) {
			LogUtil.error(logger, "压缩文件出错：{0}", e);
		}finally{
			IoUtil.close(os);
		}
        
	}
	
	/**
	 * 解压zip文件
	 * @param file 待解压源文件
	 * @param parent 解压后存放路径
	 * @param b 是否删除源文件
	 */
	@SuppressWarnings("unchecked")
	public static void deCompress(File file, File parent, boolean isDelete) {
		AssertUtil.assertTrue(file.exists() && file.isFile(), BusiErrorEnum.INPUT_NOT_EXIST, "待解压源文件不能为null/不能为文件夹");
		AssertUtil.assertTrue(StringUtils.endsWithIgnoreCase(file.getName(), FileNameUtil.ZIP_SUFFIX), BusiErrorEnum.OPERATION_ILLEGAL, "不支持的操作方式，当前只支持解压zip文件");
		
		parent = new File(parent,FileNameUtil.getBeforeName(file.getName()));
		if(!parent.exists()){
			parent.mkdirs();
		}
		
		ZipFile zip = null;
		InputStream bis = null;
        OutputStream bos = null;
        ZipEntry entry;
		File entryFile;
		byte[] buffer = new byte[1024];
		int count;
		try {
			zip = new ZipFile(file);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
			while(entries.hasMoreElements()){
				entry = entries.nextElement();
				entryFile = new File(parent,entry.getName());
				createParentIfNotExists(entryFile);
				LogUtil.info(logger, "entryFile:{0}", entryFile);
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));
	            bis = new BufferedInputStream(zip.getInputStream(entry));
	            while ((count = bis.read(buffer)) != -1) {
	                bos.write(buffer, 0, count);
	            }
	            bos.flush();
			}
			
		} catch (Exception e) {
			LogUtil.error(logger, "解压文件出错：{0}", e);
		}finally{
			
			IoUtil.close(zip,bis,bos);
			
			if(isDelete){
				deleteAllFiles(file);
			}
		}
	}

	private static void zip(ZipOutputStream os, File sourceFile, boolean dirFlag) {
		if(sourceFile.isDirectory()){
			File[] files = sourceFile.listFiles();
			for(File file:files){
				if (dirFlag) {
					recursionZip(os, file, sourceFile.getName() + File.separator);
	            } else {
	                recursionZip(os, file, "");
	            }
			}
		}else{
			recursionZip(os, sourceFile, "");
		}
	}

	private static void recursionZip(ZipOutputStream zos, File file,
			String baseDir) {
		if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileSec : files) {
                recursionZip(zos, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            byte[] buf = new byte[1024];
            int count;
            InputStream fis = null;
            try {
				fis = new BufferedInputStream(new FileInputStream(file));
				zos.putNextEntry(new ZipEntry(baseDir + file.getName()));
				while ((count = fis.read(buf)) != -1){
					zos.write(buf, 0, count);
				}
				zos.flush();
			} catch (Exception e) {
				LogUtil.error(logger, "压缩文件出错：{0}", e);
			}finally{
				IoUtil.close(fis);
			}
        }
		
	}

	public static File createParentIfNotExists(File targetFile) {
		if (!targetFile.exists()) {
			if(StringUtils.indexOf(targetFile.getName(), ".") != -1){
				File parent = targetFile.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
				return new File(parent,targetFile.getName());
			}
        }
		return targetFile;
	}
}
