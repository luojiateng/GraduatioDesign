package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: Appraise
 * @ProjectName: orderFood
 * @date: 2023/2/8 14:21
 * @author: 骆家腾
 */
public class Appraise implements Serializable {
    private Integer appraiseId;
    private Order order;
    private Integer shopId;
    private String time;
    private String context;
    /**
     * good 1
     * bad 0
     */
    private Integer type;
    private Double serveScore;
    private Double foodScore;
    private Integer postTime;

    public Integer getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(Integer appraiseId) {
        this.appraiseId = appraiseId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getServeScore() {
        return serveScore;
    }

    public void setServeScore(Double serveScore) {
        this.serveScore = serveScore;
    }

    public Double getFoodScore() {
        return foodScore;
    }

    public void setFoodScore(Double foodScore) {
        this.foodScore = foodScore;
    }

    public Integer getPostTime() {
        return postTime;
    }

    public void setPostTime(Integer postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "Appraise{" +
                "appraiseId=" + appraiseId +
                ", order=" + order +
                ", shopId=" + shopId +
                ", time='" + time + '\'' +
                ", context='" + context + '\'' +
                ", type=" + type +
                ", serveScore=" + serveScore +
                ", foodScore=" + foodScore +
                ", postTime=" + postTime +
                '}';
    }

    public Appraise() {
    }

    public Appraise(Integer appraiseId, Order order, Integer shopId, String time, String context, Integer type, Double serveScore, Double foodScore, Integer postTime) {
        this.appraiseId = appraiseId;
        this.order = order;
        this.shopId = shopId;
        this.time = time;
        this.context = context;
        this.type = type;
        this.serveScore = serveScore;
        this.foodScore = foodScore;
        this.postTime = postTime;
    }
}
