package com.ianeiu.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private final static transient Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 上传文件
     *
     * @param saveDirPath 文件保存目录
     * @param fileName    文件名
     * @param uploadFile  文件
     * @param ifBefore    文件保存地址是否增加前缀
     * @return 文件保存地址
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String uploadFile(String saveDirPath, String fileName, File uploadFile, boolean ifBefore) throws FileNotFoundException, IOException {

        File tmpFile = new File(saveDirPath);

        if (!tmpFile.exists()) {
            tmpFile.mkdirs(); //创建目录
        }

        String filePath = "";//文件保存地址
        if (ifBefore) {
            filePath = tmpFile.getPath() + "/" + RandomUtil.imageName() + "_" + fileName;
        } else {
            filePath = tmpFile.getPath() + "/" + fileName;
        }

        OutputStream os = new FileOutputStream(filePath);

        byte[] bs = new byte[1024];   // 1K的数据缓冲
        int len;// 读取到的数据长度

        // 开始读取
        FileInputStream fin = new FileInputStream(uploadFile);
        while ((len = fin.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        try {
            os.close();
            fin.close();
        } catch (IOException e) {
            logger.error("关闭流出现错误", e);
        }

        return filePath;
    }

    //-----------------------------------------
    // File ContentOper
    // ----------------------------------------

    /**
     * 快速清空一个超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     * @throws IOException
     */
    public final static boolean cleanFile(File file) throws IOException {
        try (
                FileWriter fw = new FileWriter(file)
        ) {
            fw.write("");
            return true;
        }
    }

    //-----------------------------------------
    // File Del
    // ----------------------------------------

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean delFile(String filePath) {
        File file = new File(filePath);
        return delFile(file);
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        boolean flag = false;
        if (file.exists() && file.isFile()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 删除一个目录
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public final static boolean delDir(File file) {
        List<File> files = listFileAll(file);
        if (CheckUtil.valid(files)) {
            for (File f : files) {
                if (f.isDirectory()) {
                    delDir(f);
                } else {
                    delFile(f);
                }
            }
        }
        return file.delete();
    }

    /**
     * 快速的删除超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     * @throws IOException
     */
    public final static boolean deleteBigFile(File file) throws IOException {
        return cleanFile(file) && delFile(file);
    }

    //-----------------------------------------
    // File Create
    // ----------------------------------------

    /**
     * 创建多级目录
     *
     * @param paths 需要创建的目录
     * @return 是否成功
     */
    public final static boolean createPathsIfNotExist(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdirs();
    }


    /**
     * 创建文件支持多级目录
     *
     * @param filePath 需要创建的文件
     * @return 是否成功
     * @throws IOException
     */
    public final static boolean createFilesIfNotExist(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    return file.createNewFile();
                }
            }
        }
        return false;
    }

    //-----------------------------------------
    // File Copy
    // ----------------------------------------

    /**
     * 使用NIO高效 拷贝文件
     *
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyFileWithNIO(File in, File out) throws IOException {
        try (
                FileInputStream inStream = new FileInputStream(in);
                FileOutputStream outStream = new FileOutputStream(out);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
        ) {
            // inChannel.transferTo(0, inChannel.size(), outChannel); //
            // original -- apparently has trouble copying large files on Windows

            // magic number for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position += inChannel.transferTo(position, maxCount, outChannel);
            }
        }
    }

    /**
     * 使用NIO+Buffer实现文件的读取拷贝
     *
     * @param in
     * @param out
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyFileWithNIOAndBuffer(File in, File out) throws FileNotFoundException, IOException {
        try (
                FileInputStream inStream = new FileInputStream(in);
                FileOutputStream outStream = new FileOutputStream(out);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    /**
     * 利用Buffer实现文件的读取拷贝
     *
     * @param in
     * @param out
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void copyFileWithBuffer(File in, File out) throws FileNotFoundException, IOException {
        try (
                InputStream fis = new BufferedInputStream(new FileInputStream(in));
                OutputStream fos = new BufferedOutputStream(new FileOutputStream(out));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos)
        ) {
            int content = 0;
            while ((content = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(content);
            }
        }
    }

    /**
     * 利用Buffer实现文件的读取拷贝
     *
     * @param in
     * @param out
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void copyFileWithStream(File in, File out) throws FileNotFoundException, IOException {
        try (
                InputStream fis = new FileInputStream(in);
                OutputStream fos = new FileOutputStream(out);
        ) {
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param file
     * @param outPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyFile(File file, String outPath) throws FileNotFoundException, IOException {
        File out = new File(outPath);
        copyFileWithStream(file, out);
    }

    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     * @throws IOException
     * @throws FileNotFoundException
     */
    public final static void copyDir(String filePath, String targetPath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        copyDir(file, targetPath);
    }

    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     * @throws IOException
     * @throws FileNotFoundException
     */
    public final static void copyDir(File filePath, String targetPath) throws FileNotFoundException, IOException {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            createPathsIfNotExist(targetPath);
        }
        File[] files = filePath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                String path = file.getName();
                if (file.isDirectory()) {
                    copyDir(file, targetPath + "/" + path);
                } else {
                    copyFile(file, targetPath + "/" + path);
                }
            }
        }
    }

    //-----------------------------------------
    // File List Search
    // ----------------------------------------

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path 需要处理的文件
     * @return 包含所有文件的的list
     */
    public final static List<File> listFile(String path) {
        File file = new File(path);
        return listFile(file);
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path  需要处理的文件
     * @param child 是否罗列子文件
     * @return 包含所有文件的的list
     */
    public final static List<File> listFile(String path, boolean child) {
        return listFile(new File(path), child);
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path  指定的路径
     * @param child 是否罗列子目录
     * @return
     */
    public final static List<File> listFile(File path, boolean child) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (child && file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public final static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }


    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path   需要处理的文件
     * @param filter 处理文件的filter
     * @return 返回文件列表
     */
    public final static List<File> listFileFilter(File path, FilenameFilter filter) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, filter));
                } else {
                    if (filter.accept(file.getParentFile(), file.getName())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取指定目录下的特点文件,通过后缀名过滤
     *
     * @param dirPath  需要处理的文件
     * @param postfixs 文件后缀
     * @return 返回文件列表
     */
    public final static List<File> listFileFilter(File dirPath, final String postfixs) {
        List<File> list = new ArrayList<File>();
        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, postfixs));
                } else {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(postfixs.toLowerCase())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public final static List<File> listFileAll(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFileAll(file));
                }
            }
        }
        return list;
    }

    /**
     * 在指定的目录下搜寻文个文件
     *
     * @param dirPath  搜索的目录
     * @param fileName 搜索的文件名
     * @return 返回文件列表
     */
    public final static List<File> searchFile(File dirPath, String fileName) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, fileName));
                } else {
                    String Name = file.getName();
                    if (Name.equals(fileName)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 查找符合正则表达式reg的的文件
     *
     * @param dirPath 搜索的目录
     * @param reg     正则表达式
     * @return 返回文件列表
     */
    public final static List<File> searchFileReg(File dirPath, String reg) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (CheckUtil.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, reg));
                } else {
                    String Name = file.getName();
                    if (RegexUtil.isMatche(Name, reg)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    //-----------------------------------------
    // File Info[type,lastModified]
    // ----------------------------------------

    /**
     * 获取文件后缀名
     *
     * @param file
     * @return
     */
    public final static String suffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file 需要处理的文件
     * @return 返回文件的修改时间
     */
    public final static Date modifyTime(File file) {
        return new Date(file.lastModified());
    }


    //-----------------------------------------
    // File and Byte[]
    // ----------------------------------------

    /**
     * File 转成 byte[]
     *
     * @param filePath 文件路径
     * @param del      是否删除文件
     * @return
     * @throws IOException
     */
    public static byte[] file2ByteArray(String filePath, boolean del) throws IOException {
        byte[] buffer;
        File file = new File(filePath);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)
        ) {
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
            if (file.exists() && del) {
                file.delete();
            }
        }
        return buffer;
    }

    /**
     * byte[] 转成 File
     *
     * @param byteArray  字节数组
     * @param targetPath 生成文件路径
     * @throws IOException
     */
    public static void byteArray2File(byte[] byteArray, String targetPath) throws IOException {
        File file = new File(targetPath);
        String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
        if (!file.exists()) {
            new File(path).mkdir();
        }
        try (InputStream in = new ByteArrayInputStream(byteArray);
             FileOutputStream fos = new FileOutputStream(file)
        ) {
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        }
    }


}
