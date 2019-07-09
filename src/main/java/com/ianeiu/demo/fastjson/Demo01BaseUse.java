package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo01BaseUse {

	/*
	 * map转json
	 */
	@Test
	public void mapToJson(){
		Map<String, Object> map = new HashMap<>();
		map.put("test01", "test01");
		map.put("test02", 1);
		String mapJson = JSON.toJSONString(map);
		System.out.println(mapJson);
	}
	
	/**
	 * mapList集合转json
	 */
	@Test
	public void listmapToJson() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("key1", "One");
		map1.put("key2", "Two");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("key1", "Three");
		map2.put("key2", "Four");

		list.add(map1);
		list.add(map2);
		
		String json = JSON.toJSONString(list);
		System.out.println(json);
	}
	
	/**
	 * bean转json
	 */
	@Test
	public void beanToJson(){
		User user = new User();
		user.setUserName("吾未眠");
		user.setAge(18);
		
		String json = JSON.toJSONString(user);
		System.out.println(json);
	}
	
	/**
	 * 格式化【与Demo02Feature toJsonSpecialSingle格式化效果一样】
	 */
	@Test
	public void toJsonFormat(){
		Map<String, Object> map = new HashMap<>();
		map.put("test01", "test01");
		map.put("test02", 1);
		String mapJson = JSON.toJSONString(map,true);
		System.out.println(mapJson);
	}
}
