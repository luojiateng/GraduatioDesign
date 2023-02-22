package com.jiateng.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Title: CarGoods
 * @ProjectName: orderFood
 * @date: 2023/2/10 0:09
 * @author: 骆家腾
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart implements Serializable {
    private Integer shoppingCartId;
    private String userId;
    private String shopId;
    private String goodsId;
    private String goodsName;
    private Double goodsPrice;
    private String goodsImgUrl;
    private Integer goodsCount;
}
