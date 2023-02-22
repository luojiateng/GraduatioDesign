package com.jiateng.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Title: ShopInfo
 * @ProjectName: orderFood
 * @date: 2023/1/11 0:23
 * @author: 骆家腾
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfo implements Serializable {
    private String shopId;
    private String shopImgUrl;
    private String shopName;
    private String MonthlySales;
    private String location;
    private String time;
    private String shopPhoneNumber;
    private List<Appraise> appraisesList;
    private List<StoreBean.Goods> goodsList;
}
