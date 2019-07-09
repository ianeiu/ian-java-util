package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo03Parse {
	
	private String mapJson ="";
	private String listJson ="";
	private String beanJson ="";
	
	@Before
	public void initJson(){
		Map<String, Object> map = new HashMap<>();
		map.put("test01", "test01");
		map.put("test02", 1);
		mapJson = JSON.toJSONString(map);
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("key1", "One");
		map1.put("key2", "Two");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("key1", "Three");
		map2.put("key2", "Four");
		map2.put("key3", null);
		list.add(map1);
		list.add(map2);
		listJson = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
		
		User user = new User();
		user.setUserName("吾未眠");
		user.setAge(18);
		beanJson = JSON.toJSONString(user);
		//beanJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);
	}
	
	@Test
	public void jsonToBean(){
		User user1 = JSON.parseObject(beanJson, User.class);
		System.out.println(user1.getUserName());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void jsonToList(){
		List<Map> list = JSON.parseArray(listJson, Map.class);
		for (Map<String,Object> map : list) {
			System.out.println("---------------------");
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void jsonToMap(){
		//Map<String, Object> map = JSON.parseObject(mapJson, new TypeReference<Map<String, Object>>() { });
		Map<String, Object> map = JSON.parseObject(mapJson, Map.class);

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}
}
