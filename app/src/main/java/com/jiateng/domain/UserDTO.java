package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: UserInfo
 * @ProjectName: orderFood
 * @date: 2023/1/15 17:17
 * @author: 骆家腾
 */
public class UserDTO implements Serializable {
    private Integer userId;
    private String phoneNumber;
    private String nickName;
    private String password;
    private String token;

    public String getNickName() {
        return nickName;
    }

    public UserDTO(Integer userId, String phoneNumber, String nickName, String password, String token) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.password = password;
        this.token = token;
    }

    public UserDTO() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
