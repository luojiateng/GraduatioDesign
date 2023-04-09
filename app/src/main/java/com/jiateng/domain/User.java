package com.jiateng.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Title: User
 * @ProjectName: orderFood
 * @date: 2023/1/15 16:59
 * @author: 骆家腾
 */
public class User implements Serializable {
    private Integer userId;
    private String nickName;
    private String username;
    private String password;
    private String avatarUrl;
    private String phoneNumber;
    private String sex;
    private List<Address> addresses;

    public User(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", addresses=" + addresses +
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public User() {
    }

    public User(Integer userId, String nickName, String username, String password, String avatarUrl, String phoneNumber, String sex, List<Address> addresses) {
        this.userId = userId;
        this.nickName = nickName;
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.addresses = addresses;
    }
}
