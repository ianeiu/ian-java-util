package com.ianeiu.demo.gson.vo;

public class UserVO {
    private String id;
    private String userName;
    private String userSex;
    private String password;


    public UserVO() {
        super();
    }

    public UserVO(String id, String userName, String userSex, String password) {
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
