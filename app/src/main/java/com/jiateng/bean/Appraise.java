package com.jiateng.bean;

import lombok.Data;

/**
 * @Description:
 * @Title: Appraise
 * @ProjectName: orderFood
 * @date: 2023/2/8 14:21
 * @author: 骆家腾
 */
@Data
public class Appraise {
    private String appraiseId;
    private String shopId;
    private String orderId;
    private String avatarUrl;
    private String userName;
    private String time;
    private String context;
    /**
     * good 1
     * bad 0
     */
    private Integer type;
    /**
     * 四舍五入
     */
    private Double serveScore;
    private Double foodScore;
    private Integer postTime;
}
