package com.jiateng.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description:
 * @Title: UserInfo
 * @ProjectName: orderFood
 * @date: 2023/1/15 17:17
 * @author: 骆家腾
 */
public class UserDTO  implements Serializable {
    private String userId;
    private String password;
    private String token;

    public UserDTO(String userId, String password, String token) {
        this.userId = userId;
        this.password = password;
        this.token = token;
    }

    public UserDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
