package com.ianeiu.demo.lombok;

import lombok.Cleanup;
import lombok.val;
import lombok.var;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        UserInfoVO userInfoVO;
        //NoArgsConstructor
        userInfoVO = new UserInfoVO();
        //RequiredArgsConstructor
        userInfoVO = new UserInfoVO("123");
        // NonNull java.lang.NullPointerException: id is marked non-null but is null
        //userInfoVO = new UserInfoVO(null);
        //AllArgsConstructor - staticName
        userInfoVO = UserInfoVO.newInstance("123","wuweimian",18,null);
        //Getter
        System.out.println(userInfoVO.getAge());
        //Setter  Accessors - chain - true Wither
        userInfoVO.setAge(20).setId("666").withId("123");
        //ToString
        System.out.println(userInfoVO.toString());
        //Builder
        userInfoVO = UserInfoVO.builder().id("321").userName("weimianwu").age(18).build();
        System.out.println(userInfoVO);
        userInfoVO = UserInfoVO.builder().id("321").userName("weimianwu").age(18).girlfriend("66").girlfriend("fa").build();
        System.out.println(userInfoVO);

    }

    @Test
    public void testDefi(){
        var name = "test";
        System.out.println(name);
        val num = 50;
        System.out.println(num);
    }

    @Test
    public void testClean(){
        try {
            //Cleanup 释放资源
            @Cleanup FileInputStream fis = new FileInputStream(new File("a.txt"));
            @Cleanup FileOutputStream fos = new FileOutputStream("b.txt");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
