package com.jiateng.domain;

import com.alibaba.fastjson2.annotation.JSONType;

import java.io.Serializable;

/**
 * @Description:
 * @Title: User
 * @ProjectName: orderFood
 * @date: 2023/1/15 16:59
 * @author: 骆家腾
 */
@JSONType(orders = {"username", "password"})
public class User implements Serializable {
    private Integer userId;
    private String nickName;
    private String phoneNumber;
    private String password;
    private String avatarUrl;
    private String username;
    private String sex;

    public User(Integer userId) {
        this.userId = userId;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public User(String nickName) {
        this.nickName = nickName;
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(Integer userId, String nickName, String phoneNumber, String password, String avatarUrl, String username, String sex) {
        this.userId = userId;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.sex = sex;
    }
}
