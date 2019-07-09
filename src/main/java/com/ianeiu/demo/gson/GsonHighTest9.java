package com.ianeiu.demo.gson;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/** 
* @author xuanyouwu 
* @email xuanyouwu@163.com 
* @time 2016-05-18 11:20 
*/  
public class GsonHighTest9 {  
   private static void log(String msg) {  
       System.out.println(msg);  
   }  
 
   public static class User {  
       public String name;  
       @JsonAdapter(IntegerTypeAdapter.class)  
       public int age;  
 
       @Override  
       public String toString() {  
           return "User{" +  
                   "name='" + name + '\'' +  
                   ", age=" + age +  
                   '}';  
       }  
   }  
 
   public static class IntegerTypeAdapter extends TypeAdapter<Integer> {  
 
       @Override  
       public void write(JsonWriter out, Integer value) throws IOException {  
           out.value(value);  
       }  
 
       @Override  
       public Integer read(JsonReader in) throws IOException {  
           int i = 0;  
           try {  
               String str = in.nextString();  
               i = Integer.valueOf(str);  
           } catch (Exception e) {  
           }  
           return i;  
       }  
   }  
 
   public static class User2 {  
       public String name;  
       public int age;  
 
       @Override  
       public String toString() {  
           return "User{" +  
                   "name='" + name + '\'' +  
                   ", age=" + age +  
                   '}';  
       }  
   }  
 
 
   public static void main(String[] args) throws Exception {  
       Gson gson = new Gson();  
 
       String jsonStrFromServer = "{\n" +  
               "    \"age\": \"\",\n" +  
               "    \"name\": \"zhangsan\"\n" +  
               "}";  
       log("------->jsonFromServer:" + jsonStrFromServer);  
       try {  
           User2 user2 = gson.fromJson(jsonStrFromServer, User2.class);  
           log("------>gson 解析:" + user2);  
       } catch (Exception e) {  
           log("------>gson 解析异常:" + e);  
       }  
 
 
       try {  
           User user = gson.fromJson(jsonStrFromServer, User.class);  
           log("------>JsonAdapter 注解 解析:" + user);  
       } catch (JsonParseException e) {//java.lang.NumberFormatException: empty String  
           log("------>JsonAdapter 注解 异常:" + e);  
       }  
   }  
}  
