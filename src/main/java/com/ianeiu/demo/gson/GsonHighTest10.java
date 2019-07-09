package com.ianeiu.demo.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author xuanyouwu
 * @email xuanyouwu@163.com
 * @time 2016-05-18 11:20
 */
public class GsonHighTest10 {
    private static void log(String msg) {
        System.out.println(msg);
    }

    public static class User {
        public String name;
        public int age;

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }

    public static void main(String[] args) throws Exception {
        JsonDeserializer<Integer> jsonDeserializer = (json, typeOfT, context) -> {
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                return 0;
            }
        };
        Gson gson = new GsonBuilder().registerTypeAdapter(int.class,jsonDeserializer).create();

        String jsonStrFromServer = "{\n" + "    \"age\": \"\",\n    \"name\": \"zhangsan\"\n }";
        log("------->jsonFromServer:" + jsonStrFromServer);
        try {
            User user = gson.fromJson(jsonStrFromServer, User.class);
            log("------>  JsonDeserializer<Integer> 解析:" + user);
        } catch (Exception e) {
            log("------>  JsonDeserializer<Integer> 解析异常:" + e);
        }

        Gson gson1 = new Gson();
        try {
            User user1 = gson1.fromJson(jsonStrFromServer, User.class);
            log("------> 默认gson 解析:" + user1);
        } catch (Exception e) {
            log("------>  默认gson 解析异常:" + e);
        }

    }
}