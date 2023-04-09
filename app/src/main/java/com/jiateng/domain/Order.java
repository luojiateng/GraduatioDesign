package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: HistoryOrder
 * @ProjectName: orderFood
 * @date: 2023/1/11 12:56
 * @author: 骆家腾
 */
public class Order  implements Serializable {
    private Integer orderId;
    private User user;
    private Shop shop;
    private Address address;
    private ShoppingCart shoppingCartList;
    private String status;
    private Double money;
    private String startTimeOfOrder;
    private String remark;

    public Order() {
    }

    public Order(Integer orderId, User user, Shop shop, Address address, ShoppingCart shoppingCartList, String status, Double money, String startTimeOfOrder, String remark) {
        this.orderId = orderId;
        this.user = user;
        this.shop = shop;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ShoppingCart getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(ShoppingCart shoppingCartList) {
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
                ", address=" + address +
                ", shoppingCartList=" + shoppingCartList +
                ", status='" + status + '\'' +
                ", money=" + money +
                ", startTimeOfOrder='" + startTimeOfOrder + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
