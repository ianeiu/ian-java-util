package com.ianeiu.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 * 文件编码相关的一些工具函数
 */
public class FileEncodingUtil {
    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param fileName        要转换的文件
     * @param fromCharsetName 源文件的编码
     * @param toCharsetName   要转换的编码
     */
    public static void convert(String fileName, String fromCharsetName, String toCharsetName) {
        convert(new File(fileName), fromCharsetName, toCharsetName, null);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param file            要转换的文件或目录
     * @param fromCharsetName 源文件的编码
     * @param toCharsetName   要转换的编码
     */
    public static void convert(File file, String fromCharsetName, String toCharsetName) {
        convert(file, fromCharsetName, toCharsetName, null);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param fileName        要转换的文件或目录
     * @param fromCharsetName 源文件的编码
     * @param toCharsetName   要转换的编码
     * @param filter          文件名过滤器
     */
    public static void convert(String fileName, String fromCharsetName, String toCharsetName, FilenameFilter filter) {
        convert(new File(fileName), fromCharsetName, toCharsetName, filter);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param file            要转换的文件或目录
     * @param fromCharsetName 源文件的编码
     * @param toCharsetName   要转换的编码
     * @param filter          文件名过滤器
     */
    public static void convert(File file, String fromCharsetName, String toCharsetName, FilenameFilter filter) {
        if (file.isDirectory()) {
            List<File> list = CheckUtil.valid(filter) ? FileUtil.listFileFilter(file, filter) :
                    FileUtil.listFile(file);
            if (CheckUtil.valid(list)) {
                for (File f : list) {
                    convert(f, fromCharsetName, toCharsetName, filter);
                }
            }
        } else {
            if (filter == null || filter.accept(file.getParentFile(), file.getName())) {
                String fileContent = getFileContentFromCharset(file, fromCharsetName);
                saveFileWithCharset(file, toCharsetName, fileContent);
            }
        }
    }

    /**
     * 以指定编码方式读取文件，返回文件内容
     *
     * @param file            要转换的文件
     * @param fromCharsetName 源文件的编码
     */
    public static String getFileContentFromCharset(File file, String fromCharsetName) {
        String str = "";
        if (!Charset.isSupported(fromCharsetName)) {
            throw new UnsupportedCharsetException(fromCharsetName);
        }
        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(inputStream, fromCharsetName)
        ) {
            char[] chs = new char[(int) file.length()];
            reader.read(chs);
            str = new String(chs).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 以指定编码方式写文本文件，存在会覆盖
     *
     * @param file          要写入的文件
     * @param toCharsetName 要转换的编码
     * @param content       文件内容
     */
    public static void saveFileWithCharset(File file, String toCharsetName, String content) {
        if (!Charset.isSupported(toCharsetName)) {
            throw new UnsupportedCharsetException(toCharsetName);
        }
        try (
                OutputStream outputStream = new FileOutputStream(file);
                OutputStreamWriter outWrite = new OutputStreamWriter(outputStream, toCharsetName)
        ) {
            outWrite.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * **************************************************
     * 以下方式利用mozilla的jchardet作为探测工具
     */

    private static boolean found    = false;
    /**
     * 如果完全匹配某个字符集检测算法, 则该属性保存该字符集的名称. 否则(如二进制文件)其值就为默认值 null, 这时应当查询属性
     */
    private static String  encoding = null;

   
    /**
	 * 获取文件的编码
	 *
	 * @param file 需要处理文件的编码
	 * @param det  nsDetector
	 * @return 返回文件编码
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String geestFileEncoding(File file, nsDetector det) {
	    det.Init(new nsICharsetDetectionObserver() {
	        public void Notify(String charset) {
	            found = true;
	            encoding = charset;
	        }
	    });
	    byte[]  buf     = new byte[1024];
	    int     len;
	    boolean done    = false;
	    boolean isAscii = true;
	    try (
	            BufferedInputStream imp = new BufferedInputStream(new FileInputStream(file));
	    ) {
	        while ((len = imp.read(buf, 0, buf.length)) != -1) {
	            // Check if the stream is only ascii.
	            if (isAscii) {
	                isAscii = det.isAscii(buf, len);
	            }
	
	            // DoIt if non-ascii and not done yet.
	            if (!isAscii && !done) {
	                done = det.DoIt(buf, len, false);
	            }
	        }
	        det.DataEnd();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	
	    if (isAscii) {
	        encoding = "ASCII";
	        found = true;
	    }
	
	    if (!found) {
	        String prob[] = det.getProbableCharsets();
	        if (prob.length > 0) {
	            // 在没有发现情况下，则取第一个可能的编码
	            encoding = prob[0];
	        } else {
	            return null;
	        }
	    }
	    return encoding;
	}

	/**
     * 传入一个文件(File)对象，检查文件编码
     *
     * @param file File对象实例
     * @return 文件编码，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String guestFileEncoding(File file) throws IOException {
        return geestFileEncoding(file, new nsDetector());
    }

    /**
     * 获取文件的编码
     *
     * @param file         File对象实例
     * @param languageHint 语言提示区域代码 eg：1 : Japanese; 2 : Chinese; 3 : Simplified Chinese;
     *                     4 : Traditional Chinese; 5 : Korean; 6 : Dont know (default)
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String guestFileEncoding(File file, int languageHint) throws IOException {
        return geestFileEncoding(file, new nsDetector(languageHint));
    }

    /**
     * 获取文件的编码
     *
     * @param path 文件路径
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String guestFileEncoding(String path) throws IOException {
        return guestFileEncoding(new File(path));
    }

    /**
     * 获取文件的编码
     *
     * @param path         文件路径
     * @param languageHint 语言提示区域代码 eg：1 : Japanese; 2 : Chinese; 3 : Simplified Chinese;
     *                     4 : Traditional Chinese; 5 : Korean; 6 : Dont know (default)
     * @return 返回文件的编码
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String guestFileEncoding(String path, int languageHint) throws FileNotFoundException, IOException {
        return guestFileEncoding(new File(path), languageHint);
    }
}
