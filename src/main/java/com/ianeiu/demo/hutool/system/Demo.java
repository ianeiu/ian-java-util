package com.ianeiu.demo.hutool.system;

import cn.hutool.core.lang.Console;
import cn.hutool.system.SystemUtil;
import org.junit.Test;

public class Demo {

    @Test
    public void test(){
        Console.log(SystemUtil.getJvmSpecInfo());
        Console.log(SystemUtil.getJvmInfo());
        Console.log(SystemUtil.getJavaSpecInfo());
        Console.log(SystemUtil.getJavaInfo());
        Console.log(SystemUtil.getJavaRuntimeInfo());
        Console.log(SystemUtil.getOsInfo());
        Console.log(SystemUtil.getUserInfo());
        Console.log(SystemUtil.getHostInfo());
        Console.log(SystemUtil.getRuntimeInfo());
    }
}
