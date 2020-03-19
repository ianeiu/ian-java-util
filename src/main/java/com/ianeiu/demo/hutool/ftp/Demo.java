package com.ianeiu.demo.hutool.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.ftp.Ftp;

public class Demo {


    public static void main(String[] args) {
        Ftp ftp = new Ftp("127.0.0.1", 21, "test", "test123");
        if (!ftp.exist("666")) {
            ftp.mkdir("666");
        }
        ftp.upload("666", FileUtil.file("d:/test.png"));
        Console.log(ftp.lsFiles("/"));
    }

}
