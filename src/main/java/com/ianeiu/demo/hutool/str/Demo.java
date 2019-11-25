package com.ianeiu.demo.hutool.str;

import cn.hutool.core.util.StrUtil;

/**
 * @author: wuweimian
 * @date: 2019/11/1 18:07
 * @description: 字符串工具-StrUtil
 */
public class Demo {

    public static void main(String[] args) {
        //只判断是否为null或者空字符串（""）
        System.out.println(StrUtil.hasEmpty(""));
        System.out.println(StrUtil.isEmpty(""));
        //把不可见字符也算做空
        System.out.println(StrUtil.hasBlank(""));
        System.out.println(StrUtil.isBlank(""));

        //去掉字符串的前缀后缀
        System.out.println(StrUtil.removeSuffix("pretty_girl.jpg", ".jpg"));
        System.out.println(StrUtil.removePrefix("ianeiu.com", "ianeiu."));
        System.out.println(StrUtil.removeSuffixIgnoreCase("pretty_girl.JPG", ".jpg"));

        String str = "abcdefgh";
        StrUtil.sub(str, 2, 3); //c
        StrUtil.sub(str, 2, -3); //cde
        StrUtil.sub(str, 3, 2); //c

        System.out.println(StrUtil.bytes("xxxx"));
        System.out.println(StrUtil.str("xxxx"));

        String template = "{}爱{}，就像老鼠爱大米";
        System.out.println(StrUtil.format(template, "我", "你"));

        System.out.println(StrUtil.LF);
    }

}


