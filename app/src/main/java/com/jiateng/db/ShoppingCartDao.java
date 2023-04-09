package com.jiateng.db;

import com.jiateng.domain.ShoppingCart;

import java.util.List;

/**
 * @Description:
 * @Title: ShoppingCartGoodsDao
 * @ProjectName: orderFood
 * @date: 2023/2/10 23:03
 * @author: 骆家腾
 */
public interface ShoppingCartDao {

    List<ShoppingCart> queryByGoodsByUserIdShopId(Integer userId, Integer shopId);

    ShoppingCart queryOne(ShoppingCart shoppingCart);

    int insertGoods(ShoppingCart shoppingCart);

    int deleteGoods(ShoppingCart shoppingCart);

    int updateGoods(ShoppingCart shoppingCart, int opear);

    void clean(Integer userId,Integer shopId);
}
