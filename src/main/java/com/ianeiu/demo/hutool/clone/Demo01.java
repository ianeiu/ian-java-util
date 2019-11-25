package com.ianeiu.demo.hutool.clone;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author: wuweimian
 * @date: 2019/5/29 17:10
 * @description:
 */
public class Demo01 {

    public static void main(String[] args) {

        //深克隆
        testCloneByStream();

        //泛型类克隆（浅克隆）
        testClone();

    }


    private static void testCloneByStream() {
        User user = new User();
        user.setAge(4);
        User user2 = ObjectUtil.cloneByStream(user);

        System.out.println(user);
        System.out.println(user2);
        //User{name='null', age=4}
        //User{name='null', age=4}
        user2.setAge(5);
        System.out.println(user);
        System.out.println(user2);
        //User{name='null', age=4}
        //User{name='null', age=5}

        //ObjectUtil同样提供一些静态方法：clone(obj)、cloneIfPossible(obj)用于简化克隆调用
    }

    private static void testClone() {
        Cat cat = new Cat();
        cat.setAge(4);
        Cat cat2 = ObjectUtil.clone(cat);

        System.out.println(cat);
        System.out.println(cat2);
        //Cat{name='miaomiao', age=4}
        //Cat{name='miaomiao', age=4}
        cat2.setAge(5);
        System.out.println(cat);
        System.out.println(cat2);
        //Cat{name='miaomiao', age=4}
        //Cat{name='miaomiao', age=5}
    }
}
