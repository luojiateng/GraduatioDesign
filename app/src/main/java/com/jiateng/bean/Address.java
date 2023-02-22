package com.jiateng.bean;

import lombok.Data;

/**
 * @Description:
 * @Title: Address
 * @ProjectName: orderFood
 * @date: 2023/1/15 16:59
 * @author: 骆家腾
 */
@Data
public class Address {
    private String addressId;
    private String province;
    private String city;
    private String county;
    private String schoolName;
    private String build;
    private String specificAddress;

    @Override
    public String toString() {
        return province + " " + city + " " + county + " " + schoolName + " " + build + " " + specificAddress;
    }
}
