package com.ianeiu.demo.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ianeiu.demo.gson.vo.UserVOFoExpose;
import com.ianeiu.demo.gson.vo.UserVOForSeriName;
import com.ianeiu.demo.gson.vo.UserVOFoVersion;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wm
 */
public class Demo03ObjAnno {
    private Gson gson;

    private static void log(String msg) {
        System.out.println(msg);
    }

    @Before
    public void init() {
        gson = new Gson();
    }

    @Test
    public void testJsonStrConvertSimpleBean() {
        String voStr = "{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"pwd\":\"asdad\"}";
        UserVOForSeriName vo = gson.fromJson(voStr,
                UserVOForSeriName.class);
        log("------->json convert JavaBean:" + vo);
    }

    @Test
    public void testJsonStrConvertSimpleBean2() {
        String voStr = "{\"id\":\"12123sds\",\"name\":\"吾未眠\",\"userSex\":\"man\",\"pwd\":\"asdad\"}";
        UserVOForSeriName vo = gson.fromJson(voStr,
                UserVOForSeriName.class);
        log("------->json convert JavaBean:" + vo);
    }

    @Test
    public void testExpose() {
        String jsonFromServer = "{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"password\":\"asdad\"}";
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        UserVOFoExpose user = gson.fromJson(jsonFromServer, UserVOFoExpose.class);
        log("------>反序列化:" + user);

        UserVOFoExpose user1 = new UserVOFoExpose();
        user1.setUserName("老吾");
        user1.setId("1321sad");
        user1.setPassword("asdasd");
        user1.setUserSex("male");
        String userStr = gson.toJson(user1);
        log("------>序列化:" + userStr);
    }

    @Test
    public void testSince() {
        String jsonFromServer = "{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"password\":\"asdad\"}";
        Gson gson = new GsonBuilder().setVersion(1)// 版本为1
                .create();
        UserVOFoVersion vo = gson.fromJson(jsonFromServer, UserVOFoVersion.class);
        log("------>反序列化v=1:" + vo);

        UserVOFoVersion user = new UserVOFoVersion();
        user.setUserName("老吾");
        user.setId("1321sad");
        user.setPassword("asdasd");
        user.setUserSex("male");
        String userStr = gson.toJson(user);
        log("------>序列化v=1:" + userStr);


        Gson gson2 = new GsonBuilder().setVersion(2)// 版本为2
                .create();
        UserVOFoVersion vo2 = gson2.fromJson(jsonFromServer, UserVOFoVersion.class);
        log("------>反序列化v=2:" + vo2);
        UserVOFoVersion user2 = new UserVOFoVersion();
        user2.setUserName("老吾");
        user2.setId("1321sad");
        user2.setPassword("asdasd");
        user2.setUserSex("male");
        String userStr2 = gson2.toJson(user2);
        log("------>反序列化v=1:" + userStr2);

    }

    @Test
    public void testUntil() {
        String jsonFromServer = "{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"password\":\"asdad\"}";
        Gson gson = new GsonBuilder().setVersion(1).create();
        UserVOFoVersion vo = gson.fromJson(jsonFromServer, UserVOFoVersion.class);
        log("------>反序列化v=1:" + vo);

        UserVOFoVersion user = new UserVOFoVersion("1321sad", "老吾", "male", "asdasd");
        String userStr = gson.toJson(user);
        log("------>序列化v=1:" + userStr);

        Gson gson2 = new GsonBuilder().setVersion(2).create();
        UserVOFoVersion vo2 = gson2.fromJson(jsonFromServer, UserVOFoVersion.class);
        log("------>反序列化v=2:" + vo2);
        UserVOFoVersion user2 = new UserVOFoVersion("1321sad", "老吾", "male", "asdasd");
        String userStr2 = gson2.toJson(user2);
        log("------>序列化v=2:" + userStr2);
    }
}
