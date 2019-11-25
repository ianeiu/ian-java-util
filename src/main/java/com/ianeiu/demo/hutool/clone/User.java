package com.ianeiu.demo.hutool.clone;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: wuweimian
 * @Date: 2019/5/30 17:03
 * @Description:
 */
@Data
public class User implements Serializable {
    private String name;
    private int age;
}
