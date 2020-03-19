package com.ianeiu.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * FTP工具类
 *
 * @author wm
 */
public class FTPUtil {
    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    static String ftpHost;
    static String ftpPassword;
    static String ftpUserName;
    static int ftpPort;

    private static void initFtpInfo() throws IOException {
        String ftpInfoUrl = FTPUtil.class.getResource("ftp-conf.properties").getPath();//正式环境
        //String ftpInfoUrl = "G:\\ftp-conf.properties";//本地测试
        Properties ps = new Properties();
        ps.load(new FileInputStream(ftpInfoUrl));
        ftpHost = ps.getProperty("host");
        ftpPassword = ps.getProperty("pwd");
        ftpUserName = ps.getProperty("user");
        ftpPort = Integer.parseInt(ps.getProperty("post"));
    }

    public static FTPClient getFTPClient() throws Exception {
        FTPClient ftpClient = new FTPClient();
        initFtpInfo();
        ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
        boolean isLogin = ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
        if (!isLogin) {
            throw new Exception("连接FTP出现错误，原因：用户名或密码错误");
        }
        return ftpClient;
    }

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口
     * @return FTPClient对象
     * @throws Exception
     * @throws IOException
     * @throws SocketException
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpPassword, String ftpUserName, int ftpPort) throws IOException {
        FTPClient ftpClient = null;
        ftpClient = new FTPClient();
        ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
        ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            logger.info("未连接到FTP");
            ftpClient.disconnect();
        } else {
            logger.info("FTP连接成功。");
        }
        return ftpClient;
    }

    /**
     * 获取文件输入流
     *
     * @param ftpClient
     * @param ftpPath
     * @param fileName
     * @param charset   文件内容编码
     * @return 输入流-需调用方法使用完关闭
     * @throws Exception
     */
    public static InputStream getInputStream(FTPClient ftpClient, String ftpPath, String fileName, String charset) throws IOException {
        InputStream in;
        //ftpClient.setControlEncoding("UTF-8"); // 中文支持  
        ftpClient.setControlEncoding(charset);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(ftpPath);
        in = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "iso-8859-1"));
        return in;
    }


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器基础目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @param charset
     * @return 成功返回true，否则返回false
     * @throws IOException
     * @throws SocketException
     */
    //enterLocalPassiveMode
    public static boolean uploadFile(String host, int port, String username, String password,
                                     String path, String filename, InputStream input, String charset) {

        if (host == null) {
            return false;
        }

        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            boolean a = ftp.login(username, password);//登录

            logger.info("ftp://" + host + ":" + port + "  登陆状态：" + a);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            boolean c = createDir(path, ftp);
            logger.info("ftp://" + host + ":" + port + "  创建目录状态：" + c);

//	    	if(FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))){
//	    		// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
//	    		LOCAL_CHARSET="UTF-8";
//	    	}
            ftp.setControlEncoding(charset);
            ftp.enterLocalActiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            boolean b = ftp.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), input);
            logger.info("ftp://" + host + ":" + port + "  上传文件状态：" + b);

            ftp.logout();
            success = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("关闭input异常" + e);
            }

            try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                logger.error("disconnectFTP异常" + ioe);
            }
        }
        return success;
    }


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param ftp
     * @param path     FTP服务器文件存放路径。例：aa/bb
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @param charset
     * @return 成功返回true，否则返回false
     * @throws IOException
     * @throws SocketException
     */
    //enterLocalPassiveMode
    public static boolean uploadFile(FTPClient ftp, String path, String filename, InputStream input, String charset) throws IOException {

        boolean success = false;
        try {
            boolean c = createDir(path, ftp);
            if (!c) {
                return false;
            }
//	    	if(FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))){
//	    		// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
//	    		LOCAL_CHARSET="UTF-8";
//	    	}
            ftp.setControlEncoding(charset);
            ftp.enterLocalActiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            success = ftp.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), input);

            ftp.logout();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("关闭input异常" + e);
            }

            try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                logger.error("disconnectFTP异常" + ioe);
            }
        }
        return success;
    }

    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir 例：aa/bb
     * @return
     */
    private static boolean createDir(String dir, FTPClient ftp) {
        if (dir == null || "".equals(dir.trim())) {
            return true;
        }
        String d;
        try {
            //目录编码，解决中文路径问题  
            d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
            //尝试切入目录  
            if (ftp.changeWorkingDirectory(d)) {
                return true;
            }
            String[] arr = dir.split("/");
            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录  
            for (String s : arr) {
                sbfDir.append("/");
                sbfDir.append(s);
                //目录编码，解决中文路径问题  
                d = new String(sbfDir.toString().getBytes("GBK"), "iso-8859-1");
                //尝试切入目录  
                if (ftp.changeWorkingDirectory(d))
                    continue;
                if (!ftp.makeDirectory(d)) {
                    System.out.println("[失败]ftp创建目录：" + sbfDir.toString());
                    return false;
                }
                System.out.println("[成功]创建ftp目录：" + sbfDir.toString());
            }
            //将目录切换至指定路径  
            return ftp.changeWorkingDirectory(d);
        } catch (Exception e) {
            logger.error("FTP创建目录失败：" + e.toString());
        }
        return false;
    }

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param ftp      ftpClient
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @return
     * @throws IOException
     */
    public static List<String> list(FTPClient ftp, String pathName) throws IOException {
        List<String> arFiles = new ArrayList<String>();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            ftp.changeWorkingDirectory(pathName);
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    arFiles.add(pathName + file.getName());
                } /*else if (file.isDirectory()) {
                    // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(ftp,pathName + file.getName() + "/");
                    }
                }*/
            }
        }
        return arFiles;
    }

    /**
     * 递归遍历目录下面指定的文件名
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ext      文件的扩展名
     * @return
     * @throws IOException
     */
    public static List<String> list(FTPClient ftp, String pathName, String ext) throws IOException {
        List<String> arFiles = new ArrayList<String>();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            ftp.changeWorkingDirectory(pathName);
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(ext)) {
                        arFiles.add(pathName + file.getName());
                    }
                } /*else if (file.isDirectory()) {
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(ftp,pathName + file.getName() + "/", ext);
                    }
                }*/
            }
        }
        return arFiles;
    }

}  