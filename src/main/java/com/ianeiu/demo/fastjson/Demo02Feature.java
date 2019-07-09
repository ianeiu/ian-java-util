package com.ianeiu.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

import java.util.*;

public class Demo02Feature {
	
	/**
	 * 特性-date
	 */
	@Test
	public void toJsonSpecialDate() {
		String dateJson = JSON.toJSONString(new Date());
		System.out.println(dateJson);

		dateJson = JSON.toJSONString(new Date(), SerializerFeature.WriteDateUseDateFormat);
		System.out.println(dateJson);
		
		dateJson = JSON.toJSONStringWithDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(dateJson);
	}
	
	/**
	 * 特性-格式 单引号、美化、输出空
	 */
	@Test
	public void toJsonSpecialFormat() {
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
		
		//单引号
		String listJson = JSON.toJSONString(list, SerializerFeature.UseSingleQuotes);
		System.out.println(listJson);
		
		//美化【与Demo01BaseUse toJsonFormat效果一样】
		listJson = JSON.toJSONString(list, SerializerFeature.PrettyFormat);
		System.out.println(listJson);
		
		//输出空
		listJson = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
		System.out.println(listJson);
	}
	
	/**
	 * 序列化是写入类型信息
	 */
	@Test
	public void toJsonSpecialClassInfo() {
		//添加autotype白名单
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true); 
		
		User user = new User();

		user.setAge(18);
		user.setUserName("李四");

		//由于序列化带了类型信息，使得反序列化时能够自动进行类型识别。
		String json = JSON.toJSONString(user, SerializerFeature.WriteClassName);
		System.out.println(json);
		
		//如果User序列化是没有加入类型信息（SerializerFeature.WriteClassName），就会报错（java.lang.ClassCastException）。
		User user1 = (User) JSON.parse(json);
		System.out.println(user1.getAge());
	}
}
