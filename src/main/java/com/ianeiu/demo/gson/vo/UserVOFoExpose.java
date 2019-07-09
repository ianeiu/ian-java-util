package com.ianeiu.demo.gson.vo;

import com.google.gson.annotations.Expose;

/**
 * 可以排除不需要序列化的字段,需要配合GsonBuilder使用
 * Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
 * 不添加@Expose注解的字段将不会解析
 */
public class UserVOFoExpose {
    private String id;
    /**
     * 不可序列化
     */
    @Expose(serialize = false)
    private String userName;
    /**
     * 不可以反序列化
     */
    @Expose(deserialize = false)
    private String userSex;
    @Expose
    private String password;


    public UserVOFoExpose() {
        super();
    }

    public UserVOFoExpose(String id, String userName, String userSex, String password) {
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
