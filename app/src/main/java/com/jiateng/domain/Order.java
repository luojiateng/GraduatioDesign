package com.jiateng.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Description:
 * @Title: HistoryOrder
 * @ProjectName: orderFood
 * @date: 2023/1/11 12:56
 * @author: 骆家腾
 */
public class Order implements Serializable {
    private Integer orderId;
    private User user;
    private Shop shop;
    private AddressInfo addressInfo;
    private ArrayList<ShoppingCart> shoppingCartList;
    private String status;
    private Double money;
    private String startTimeOfOrder;
    private String remark;
    private String type;

    public String getEndTimeOfOrder() {
        return endTimeOfOrder;
    }

    public void setEndTimeOfOrder(String endTimeOfOrder) {
        this.endTimeOfOrder = endTimeOfOrder;
    }

    private String endTimeOfOrder;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Order() {
    }

    public Order(Integer orderId, User user, Shop shop, AddressInfo addressInfo, ArrayList<ShoppingCart> shoppingCartList, String status, Double money, String startTimeOfOrder, String remark) {
        this.orderId = orderId;
        this.user = user;
        this.shop = shop;
        this.addressInfo = addressInfo;
        this.shoppingCartList = shoppingCartList;
        this.status = status;
        this.money = money;
        this.startTimeOfOrder = startTimeOfOrder;
        this.remark = remark;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public ArrayList<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(ArrayList<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getStartTimeOfOrder() {
        return startTimeOfOrder;
    }

    public void setStartTimeOfOrder(String startTimeOfOrder) {
        this.startTimeOfOrder = startTimeOfOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", shop=" + shop +
                ", address=" + addressInfo +
                ", shoppingCartList=" + shoppingCartList +
                ", status='" + status + '\'' +
                ", money=" + money +
                ", startTimeOfOrder='" + startTimeOfOrder + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
