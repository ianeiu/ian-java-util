package com.ianeiu.demo.gson.vo;

import com.google.gson.annotations.SerializedName;

public class UserVOForSeriName {
    private String id;
    @SerializedName(value = "userName", alternate = {"name", "user"})
    private String userName;
    private String userSex;
    @SerializedName("pwd")
    private String password;


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
