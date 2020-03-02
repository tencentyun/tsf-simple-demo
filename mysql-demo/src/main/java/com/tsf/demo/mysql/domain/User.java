package com.tsf.demo.mysql.domain;

/**
 * Created by chazling on 2019/7/16.
 */
public class User {

    private String user_name = "";
    private String user_token = "";

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", user_token='" + user_token + '\'' +
                '}';
    }
}