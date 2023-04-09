package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: ShopInfo
 * @ProjectName: orderFood
 * @date: 2023/1/11 0:23
 * @author: 骆家腾
 */
public class Shop implements Serializable {
    private Integer shopId;
    private String shopName;
    private String shopImageUrl;
    private Integer monthlySales;
    private Address address;
    private String beginTime;
    private String endTime;
    private String shopPhone;

    public Shop(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopImageUrl='" + shopImageUrl + '\'' +
                ", monthlySales=" + monthlySales +
                ", address=" + address +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", shopPhone='" + shopPhone + '\'' +
                '}';
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public Integer getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Shop(Integer shopId, String shopName, String shopImageUrl, Integer monthlySales, Address address, String beginTime, String endTime, String shopPhone) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopImageUrl = shopImageUrl;
        this.monthlySales = monthlySales;
        this.address = address;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shopPhone = shopPhone;
    }

    public Shop() {
    }
}
