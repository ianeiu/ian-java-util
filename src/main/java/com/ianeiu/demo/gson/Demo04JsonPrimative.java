package com.ianeiu.demo.gson;

import com.google.gson.JsonPrimitive;

/**
 * JsonPrimitive非常有意思,我们知道如果json转换成字符串
 * 可能包含引号的转义，但是通过JsonPrimative我们可以获得为转义的字符串
 * 
 * @author wm
 *
 */
public class Demo04JsonPrimative {
	private static void log(String msg) {
		System.out.println(msg);
	}

	public static void main(String[] args) throws Exception {
		String voStr = "{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"password\":\"asdad\"}";
		log("------>voStr:" + voStr+"\n");
		JsonPrimitive jsonPrimitive = new JsonPrimitive(voStr);
		log("------>jsonPrimitive:" + jsonPrimitive);
		log("------>jsonPrimitiveToString:" + jsonPrimitive.toString());// 介里
		log("------>jsonPrimitiveGetAsString:" + jsonPrimitive.getAsString());
		log("");
		JsonPrimitive jsonPrimitive2 = new JsonPrimitive("this is String");
		log("------>jsonPrimitive2:" + jsonPrimitive2);
		log("------>jsonPrimitive2ToString:" + jsonPrimitive2.toString());
		log("------>jsonPrimitive2GetAsString:" + jsonPrimitive2.getAsString());
	}
}
