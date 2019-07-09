package com.ianeiu.demo.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author xuanyouwu
 * @email xuanyouwu@163.com
 * @time 2016-05-18 11:20
 */
public class GsonHighTest8 {
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

	public static class UserTypeAdapter extends TypeAdapter<User> {

		@Override
		public void write(JsonWriter out, User value) throws IOException {
			out.beginObject();
			out.name("name").value(value.name);
			out.name("age").value(value.age);
			out.endObject();
		}

		@Override
		public User read(JsonReader in) throws IOException {
			User user = new User();
			in.beginObject();
			while (in.hasNext()) {
				switch (in.nextName()) {
				case "name":
					user.name = in.nextString();
					break;
				case "age":
					try {
						String str = in.nextString();
						user.age = Integer.valueOf(str);
					} catch (Exception e) {
					}
					break;
				}
			}
			in.endObject();
			return user;
		}
	}

	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();

		String jsonStrFromServer = "{\n" + "    \"age\": \"\",\n"
				+ "    \"name\": \"zhangsan\"\n" + "}";
		log("------->jsonFromServer:" + jsonStrFromServer);
		try {
			User user = gson.fromJson(jsonStrFromServer, User.class);
			log("------>默认Gson 解析:" + user);
		} catch (JsonParseException e) {// java.lang.NumberFormatException:
										// empty String
			log("------>默认Gson 解析 异常:" + e);
		}

		Gson gson2 = new GsonBuilder().registerTypeAdapter(User.class,
				new UserTypeAdapter()).create();
		try {
			User user2 = gson2.fromJson(jsonStrFromServer, User.class);
			log("------>自定义adapter 解析:" + user2);
		} catch (JsonParseException e) {
			log("------>自定义adapter 异常:" + e);
		}

		try {
			UserTypeAdapter userTypeAdapter = new UserTypeAdapter();
			User user3 = userTypeAdapter.fromJson(jsonStrFromServer);
			log("------>自定义adapter 解析2:" + user3);
		} catch (Exception e) {
			log("------>自定义adapter 异常2:" + e);
		}
	}
}