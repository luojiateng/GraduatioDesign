package com.jiateng.bean;

import java.util.List;

import lombok.Data;

/**
 * @Description:
 * @Title: HistoryOrder
 * @ProjectName: orderFood
 * @date: 2023/1/11 12:56
 * @author: 骆家腾
 */
@Data
public class Order {
    private String userId;
    private String shopId;
    private String orderId;
    private String shopName;
    private String status;
    private List<ShoppingCart> shoppingCartList;
    private Double money;
    private String startTimeOfOrder;
    private String finishTimeOfOrder;
    private String address;
    private String remark;
    private String paidMethod;
    private User user;
}
