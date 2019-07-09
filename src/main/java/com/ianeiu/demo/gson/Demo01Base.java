package com.ianeiu.demo.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ianeiu.demo.gson.vo.UserVO;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;


public class Demo01Base {
	private Gson gson;
	
	private static void log(String msg) {  
        System.out.println(msg);  
    } 
	
	@Before
	public void init(){
		gson = new Gson();
	}
	
	@Test
	public void testNew(){
		gson = new GsonBuilder()  
        .setLenient()// json宽松[gson2.6]
        .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式  
        .serializeNulls() //智能null  
        .setPrettyPrinting()// 调教格式  
        .disableHtmlEscaping() //默认是GSON把HTML 转义的  
        .create();  
	}
	
	@Test
	public void testSimpleBeanConvertJsonStr(){
		UserVO vo = new UserVO();
		vo.setId("12123sds");
		vo.setPassword("asdad");
		vo.setUserName("吾未眠");
		vo.setUserSex("man");
        String jsonStr = gson.toJson(vo);     
        log("---->javabean convert jsonStr:" + jsonStr);  
	}
	@Test
	public void testSimpleListMapConvertJsonStr() {
		Gson gson = new Gson();

		List<String> list = Arrays.asList("吴", "a", "3", "rt", "5");
		log("---->list convert jsonStr:" + gson.toJson(list));

		Map<String, Object> content = new HashMap<String, Object>();
		content.put("name", "xuanyouwu");
		content.put("age", "26");
		log("---->map convert jsonStr:" + gson.toJson(content));
	}
	
	@Test
	public void testJsonStrConvertSimpleBean(){
		String voStr="{\"id\":\"12123sds\",\"userName\":\"吾未眠\",\"userSex\":\"man\",\"password\":\"asdad\"}";
		UserVO vo = gson.fromJson(voStr, UserVO.class);  
		log("------->json convert JavaBean:"+vo);  
	}
	@Test
	public void testJsonStrConvertSimpleList(){
		String listJsonStr="[\"1\",\"a\",\"3\",\"rt\",\"5\"]";
		Type type = new TypeToken<ArrayList<String>>() {}.getType();  
		ArrayList<String> sList=gson.fromJson(listJsonStr, type);  
		log("------->json convert List:"+sList);  
	}
}
