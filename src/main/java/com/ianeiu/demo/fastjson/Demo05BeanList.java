package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Demo05BeanList {
	
	private String userListJson = "";
	
	@Before
	public void initUserJson(){
		User user = new User();
		user.setUserName("ianei");
		user.setAge(11);
		
		User user2 = new User();
		user2.setUserName("吾未眠");
		user.setAge(18);
		
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
				
		userListJson = JSON.toJSONString(list);
	}
	
	@Test
	public void jsonToUserList() {
		JSONArray jsonArray = JSONArray.parseArray(userListJson);

		// 遍历方式1
		int size = jsonArray.size();
		for (int i = 0; i < size; i++) {

			JSONObject jsonObject = jsonArray.getJSONObject(i);
			System.out.println("user:  " + jsonObject.getString("userName") + ":" + "  age:  "
					+ jsonObject.getInteger("age"));
		}

		// 遍历方式2
		for (Object obj : jsonArray) {

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println("user:  " + jsonObject.getString("userName") + ":" + "  age:  "
					+ jsonObject.getInteger("age"));
		}
	}
}
