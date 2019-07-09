package com.ianeiu.demo.gson.vo;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;


/* 
 * @Since(float v)注解  版本控制 
 * 结合GsonBuilder.setVersion(n)使用 当n>=v时 才会序列化解析
 *
 *  @Until(float v)注解 版本控制
 * 当gson的setVersion(n) n<v 才解析
 */
public class UserVOFoVersion {
	private String id;
	private String userName; 
	@Since(2)
	private String userSex;
	@Until(2)
	private String password;
	
	
	public UserVOFoVersion() {
		super();
	}

	public UserVOFoVersion(String id, String userName, String userSex, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.userSex = userSex;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", userName=" + userName + ", userSex="
				+ userSex + ", password=" + password + "]";
	}
	
	
}
