package com.ianeiu.demo.hutool.clone;

import cn.hutool.core.clone.CloneSupport;

import java.io.Serializable;

/**
 * 狗狗类，用于继承CloneSupport类
 * @author Looly
 *
 */
public class Dog extends CloneSupport<Dog> implements Serializable {
    private String name = "wangwang";
    private int age = 3;
}
