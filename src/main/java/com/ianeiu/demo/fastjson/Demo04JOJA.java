package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jsonobject jsonarray
 * @author wm
 * @date 2018年7月31日
 */
public class Demo04JOJA {

	@Test
	public void jsonToJO() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "One");
		map.put("key2", "Two");
		String json = JSON.toJSONString(map);
		JSONObject j = JSONObject.parseObject(json);
		
		j.put("key3", "Three");
		
		System.out.println(j.get("key1"));
		System.out.println(j.get("key2"));
		System.out.println(j.get("key3"));
	}
	
	@Test
	public void mapToJO() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "One");
		map.put("key2", "Two");

		JSONObject j = new JSONObject(map);

		j.put("key3", "Three");

		System.out.println(j.get("key1"));
		System.out.println(j.get("key2"));
		System.out.println(j.get("key3"));
	}
	
	@Test
	public void joToJson(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "One");
		map.put("key2", "Two");
	    JSONObject jsonObject = new JSONObject(map);

	    String jsonString = JSONObject.toJSONString(jsonObject);
	    // 第二种方式
	    //String jsonString = jsonObject.toJSONString();
	    
	    System.out.println(jsonString);
	}
	
	@Test
	public void listjsonToJA() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "One");
		map.put("key2", "Two");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("key1", "Three");
		map2.put("key2", "Four");

		list.add(map);
		list.add(map2);

		JSONArray j = JSONArray.parseArray(JSON.toJSONString(list));

		for (int i = 0; i < j.size(); i++) {
			System.out.println(j.get(i));
		}
	}
	
	@Test
	public void jaToListjson() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "One");
		map.put("key2", "Two");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("key1", "Three");
		map2.put("key2", "Four");

		list.add(map);
		list.add(map2);

		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));

		;
	    //第一种方式
	    String jsonString = JSONArray.toJSONString(jsonArray);
	    // 第二种方式
	    //String jsonString = jsonArray.toJSONString(jsonArray);
	    System.out.println(jsonString);
	}
}
