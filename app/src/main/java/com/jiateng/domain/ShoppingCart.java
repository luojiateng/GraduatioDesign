package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: CarGoods
 * @ProjectName: orderFood
 * @date: 2023/2/10 0:09
 * @author: 骆家腾
 */
public class ShoppingCart implements Serializable {
    private Integer shoppingCartId;
    private User user;
    private Shop shop;
    private StoreBean.Goods goods;
    private Integer goodsCount;

    public ShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Shop shop = new Shop(Integer.parseInt(shoppingCartDTO.getShopId()));
        this.user = new User(Integer.parseInt(shoppingCartDTO.getUserId()));
        this.shop = shop;
        this.goods = new StoreBean.Goods(
               Integer.parseInt(shoppingCartDTO.getGoodsId()),
                shop,
                shoppingCartDTO.getGoodsImgUrl(),
                shoppingCartDTO.getGoodsName(),
                "",
                shoppingCartDTO.getGoodsPrice(),
                shoppingCartDTO.getGoodsCount());
        this.goodsCount = shoppingCartDTO.getGoodsCount();
    }


    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId=" + shoppingCartId +
                ", userId=" + user +
                ", shopId=" + shop +
                ", goodsId=" + goods +
                ", goodsCount=" + goodsCount +
                '}';
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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

    public StoreBean.Goods getGoods() {
        return goods;
    }

    public void setGoods(StoreBean.Goods goods) {
        this.goods = goods;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public ShoppingCart() {
    }

    public ShoppingCart(User user, Shop shop, StoreBean.Goods goods, Integer goodsCount) {
        this.user = user;
        this.shop = shop;
        this.goods = goods;
        this.goodsCount = goodsCount;
    }
}
