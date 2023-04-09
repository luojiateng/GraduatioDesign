package com.jiateng.config;

/**
 * @Description:
 * @Title: Const
 * @ProjectName: orderFood
 * @date: 2023/1/9 23:20
 * @author: 骆家腾
 */
public class Constants {
    public static final int ONE_SECOND = 1000;

    //订单状态
    public static final String ORDRE_STATUS_FINISH = "已完成";
    public static final String ORDRE_STATUS_NOT_PAID = "未支付";
    public static final String ORDRE_STATUS_DELIVERY = "配送中";
    public static final String ORDRE_STATUS_CANCEL = "订单取消";

    public static final String[] IMAGES_UTILS = {
            "https://t7.baidu.com/it/u=1049905209,2156712912&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=860330160,4117395242&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=3979143318,65711957&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=91559597,2460124813&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=2815364097,2879086091&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=2295110163,3344731801&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=4135823832,1485331801&fm=193&f=GIF",
            "https://t7.baidu.com/it/u=671375251,1357626043&fm=193&f=GIF"
    };
    public static final String PAID_METHOD = "在线支付";
}
