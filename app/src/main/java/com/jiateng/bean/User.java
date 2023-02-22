package com.jiateng.bean;

import java.util.List;

import lombok.Data;

/**
 * @Description:
 * @Title: User
 * @ProjectName: orderFood
 * @date: 2023/1/15 16:59
 * @author: 骆家腾
 */
@Data
public class User {
    private String userId;
    private String userName;
    private String avatarUrl;
    private String phoneNumber;
    private String sex;
    private List<ShopInfo> favoriteStore;
    private List<Address> address;
}
