package com.ianeiu.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @Description: ZIP工具类
 * @author: wm
 * @date: 2018年9月25日 下午4:00:19
 * @version: 1.0
 */
public class ZipUtil {

	/**
	 * 压缩格式
	 */
	public static final String EXT = ".zip";
	
	/**
	 * 上级目录,""表示不添加
	 */
	private static final String BASE_DIR = "";

	/**
	 * 符号"/"
	 */
	private static final String PATH = "/";
	
	private static final int BUFFER = 1024;
	
	/**
	 * 解密解压
	 * @param file 压缩文件
	 * @param pwd 密码
	 * @param dstPath 目的地址
	 * @throws ZipException
	 */
	public static void decompressByPwd(File file,String pwd,String dstPath) throws ZipException {
		net.lingala.zip4j.core.ZipFile srcFile = new net.lingala.zip4j.core.ZipFile(file);
		srcFile.setFileNameCharset("GBK");
		srcFile.setPassword(pwd.toCharArray());
		srcFile.extractAll(dstPath);
	}
	
	/**
	 * 加密压缩
	 * @param sourceFile 需要压缩的文件集
	 * @param orderFile 目的地址
	 * @return 压缩密码
	 */
	public static String compressWithPwd(List<File> sourceFile,String orderFile,String passwork) {
		net.lingala.zip4j.io.ZipOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			ArrayList<File> filesToAdd = new ArrayList<File>();
			for(File f : sourceFile){
				filesToAdd.add(f);
			}
			outputStream = new net.lingala.zip4j.io.ZipOutputStream(new FileOutputStream(new File(orderFile)));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(passwork);
			
			for (int i = 0; i < filesToAdd.size(); i++) {
				File file = filesToAdd.get(i);
				outputStream.putNextEntry(file,parameters);
				if (file.isDirectory()) {
					outputStream.closeEntry();
					continue;
				}
				inputStream = new FileInputStream(file);
				byte[] readBuff = new byte[4096];
				int readLen = -1;
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}
				outputStream.closeEntry();
				inputStream.close();
			}
			outputStream.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return passwork;
	}
	
	
	/**
	 * 压缩 
	 * @param srcFile
	 * @throws IOException
	 */
	public static void compress(File srcFile) throws IOException {
		String name = srcFile.getName();
		if(srcFile.isFile()){
			int extPlace = name.lastIndexOf(".");
			if(extPlace!=-1){
				name = name.substring(0,name.lastIndexOf("."));
			}
		}
		
		String basePath = srcFile.getParent();
		String destPath = basePath + PATH + name + EXT;
		compress(srcFile, destPath);
	}
	
	/**
	 * 压缩 
	 * @param srcPath
	 * @throws IOException
	 */
	public static void compress(String srcPath) throws IOException {
		File srcFile = new File(srcPath); 
		compress(srcFile);
	}

	
	/**
	 * 压缩 
	 * @param srcFile 源路径
	 * @param destFile 生成的压缩文件
	 * @throws IOException
	 */
	public static void compress(File srcFile, File destFile) throws IOException {
		/*if(!destFile.exists()){
			destFile.createNewFile();
		}*/
		// 对输出文件做CRC32校验
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
		ZipOutputStream zos = new ZipOutputStream(cos);

		compress(srcFile, zos, BASE_DIR);

		zos.flush();
		zos.close();
	}

	/**
	 * 压缩文件 
	 * @param srcFile 源路径
	 * @param destPath 文件路径
	 * @throws IOException
	 */
	public static void compress(File srcFile, String destPath) throws IOException {
		compress(srcFile, new File(destPath));
	}

	/**
	 * 压缩 文件
	 * @param srcPath  源文件路径
	 * @param destPath  目标文件路径
	 * 
	 */
	public static void compress(String srcPath, String destPath)
			throws IOException {
		File srcFile = new File(srcPath);
		compress(srcFile, destPath);
	}
	
	/**
	 * 压缩 
	 * @param srcFile 压缩文件集合
	 * @param destFile  生成的压缩文件
	 * @throws IOException
	 */
	public static void compress(File srcFile[], File destFile) throws IOException {
		// 对输出文件做CRC32校验
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		
		for(File file : srcFile)
			compress(file, zos, BASE_DIR);

		zos.flush();
		zos.close();
	}

	/**
	 * 压缩 
	 * @param srcFile  源路径
	 * @param zos  ZipOutputStream
	 * @param basePath 压缩包内相对路径
	 * @throws IOException
	 */
	private static void compress(File srcFile, ZipOutputStream zos, String basePath) throws IOException {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}

	/**
	 * 压缩目录
	 * 
	 * @param dir
	 * @param zos
	 * @param basePath
	 * @throws IOException
	 */
	private static void compressDir(File dir, ZipOutputStream zos,String basePath) throws IOException {

		File[] files = dir.listFiles();

		// 构建空目录
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);
			//org.apache.tools.zip.ZipEntry entry.setUnixMode(755);
			zos.putNextEntry(entry);
			zos.closeEntry();
		}

		for (File file : files) { 
			// 递归压缩
			compress(file, zos, basePath + PATH + dir.getName() + PATH);

		}
	}

	/**
	 * 文件压缩 
	 * @param file  待压缩文件
	 * @param zos ZipOutputStream
	 * @param dir  压缩文件中的当前路径
	 * @throws IOException
	 */
	private static void compressFile(File file, ZipOutputStream zos, String dir) throws IOException {

		ZipEntry entry = new ZipEntry(dir +PATH+ file.getName());
		//org.apache.tools.zip.ZipEntry entry.setUnixMode(644);
		zos.putNextEntry(entry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();

		zos.closeEntry();
	}
	
	
	/**
     * 文档解压 from opslabJutil
     * @param source 需要解压缩的文档名称
     * @param path   需要解压缩的路径
     */
    public final static void unCompress(File source, String path) throws IOException {
        ZipEntry zipEntry = null;
        File dir = new File(path);
        if(!dir.exists()){
        	dir.mkdirs();	
        }
        //实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
        //实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
        try (
                ZipFile zipFile = new ZipFile(source);
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(source))
        ) {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File temp = new File(path + "/" + fileName);
                if (!temp.getParentFile().exists()) {
                    temp.getParentFile().mkdirs();
                }
                try (OutputStream os = new FileOutputStream(temp);
                     //通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
                     InputStream is = zipFile.getInputStream(zipEntry)) {
                    int len = 0;
                    while ((len = is.read()) != -1) {
                        os.write(len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static void main(String[] args) {
    	File srcFile = new File("D:/DqdpUtil.java");
    	//File srcFile = new File("D:/logs/analyse");
		//File srcFile = new File("D:/中文测试");
		try {
			ZipUtil.compress(srcFile);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
    public static void main1(String[] args) {
    	String destPath = "D:/logs/GG";
    	File file = new File("D:/logs/analyse.zip");
    	try {
    		ZipUtil.unCompress(file, destPath);
    		System.out.println("success");
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
    }
}