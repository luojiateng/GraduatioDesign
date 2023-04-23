package com.jiateng.domain;

import lombok.Data;

/**
 * @Description:
 * @Title: ShoppingCartDTO
 * @ProjectName: orderFood
 * @date: 2023/4/9 18:12
 * @author: 骆家腾
 */
@Data
public class ShoppingCartDTO {
    private Integer shoppingCartId;
    private String userId;
    private String shopId;
    private String goodsId;
    private String goodsName;
    private Double goodsPrice;
    private String goodsImgUrl;
    private Integer goodsCount;

    public ShoppingCartDTO() {

    }

    public ShoppingCartDTO(ShoppingCart shoppingCart) {
        this.shoppingCartId = null;
        this.userId = shoppingCart.getUser().getUserId().toString();
        this.shopId = shoppingCart.getShop().getShopId().toString();
        this.goodsId = shoppingCart.getGoods().getGoodsId().toString();
        this.goodsName = shoppingCart.getGoods().getGoodName();
        this.goodsPrice = shoppingCart.getGoods().getPrice();
        this.goodsImgUrl = shoppingCart.getGoods().getGoodsImageUrl();
        this.goodsCount = shoppingCart.getGoodsCount();
    }
}
