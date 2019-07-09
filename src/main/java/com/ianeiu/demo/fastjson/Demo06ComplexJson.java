package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

public class Demo06ComplexJson {
    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"id\":\"123123asdads\",\"list\":[{\"userName\":\"wuweimian\",\"age\":12},{\"userName\":\"吾未眠\",\"age\":19}]}";

    /**
     * 复杂json格式字符串到JSONObject的转换
     */
    @Test
    public void complexJSONStrToJSONObject() {

        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);

        String id = jsonObject.getString("id");
        System.out.println("id:  " + id);

        JSONArray userA = jsonObject.getJSONArray("list");
        for (Object object : userA) {
            JSONObject jsonObjectone = (JSONObject) object;
            String userName = jsonObjectone.getString("userName");
            Integer age = jsonObjectone.getInteger("age");

            System.out.println("userName:  " + userName + "   age:  " + age);
        }
    }

    /**
     * 复杂json格式字符串到JavaBean_obj的转换
     */
    @Test
    public void complexJSONStrToBean() {

        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        UserGroup ug = JSONObject.parseObject(COMPLEX_JSON_STR, new TypeReference<UserGroup>() {
        });
        System.out.println(ug);

        //第二种方式,使用Gson思想
        UserGroup ug2 = JSONObject.parseObject(COMPLEX_JSON_STR, UserGroup.class);
        //UserGroup ug2 = JSON.parseObject(COMPLEX_JSON_STR,UserGroup.class);
        System.out.println(ug2);
    }

}
