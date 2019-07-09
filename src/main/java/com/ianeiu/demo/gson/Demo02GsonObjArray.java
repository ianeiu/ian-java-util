package com.ianeiu.demo.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

public class Demo02GsonObjArray {
	private static void log(String msg) {
		System.out.println(msg);
	}

	@Test
	public void testJsonObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", "xuanyouwu");
		jsonObject.addProperty("age", 26);
		log("------>create jsonObject:" + jsonObject);
	}

	@Test
	public void testJsonArrary() {
		JsonArray jsonElements = new JsonArray();
		jsonElements.add("a");
		jsonElements.add("b");
		jsonElements.add("c");
		jsonElements.add("d");

		log("------>create jsonArray:" + jsonElements);
	}

	@Test
	public void testComplexJson() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("name", "xuanyouwu");
		jsonObject2.addProperty("age", 26);
		JsonArray jsonElements2 = new JsonArray();
		jsonElements2.add("骑车");
		jsonElements2.add("打游戏");
		jsonElements2.add("看电视");
		jsonObject2.add("hobby", jsonElements2);
		log("------>create jsonObject inner JsonArray:" + jsonObject2);
	}
}
