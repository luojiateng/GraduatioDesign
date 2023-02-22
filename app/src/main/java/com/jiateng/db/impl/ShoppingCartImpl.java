package com.jiateng.db.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jiateng.bean.ShoppingCart;
import com.jiateng.common.base.BaseSQLiteHelper;
import com.jiateng.db.ShoppingCartDao;

import java.util.List;

/**
 * @Description:
 * @Title: ShoppingCartImpl
 * @ProjectName: orderFood
 * @date: 2023/2/10 23:07
 * @author: 骆家腾
 */
public class ShoppingCartImpl extends BaseSQLiteHelper<ShoppingCart> implements ShoppingCartDao {

    public static ShoppingCartImpl getInstance(Context context) {
        if (helper == null) {
            helper = new ShoppingCartImpl(context, "shoppingcart", 1);
        }
        helper.openRWLink();
        return (ShoppingCartImpl) helper;
    }

    public ShoppingCartImpl(Context context, String tableName, Integer dbVersion) {
        super(context, tableName, dbVersion);
    }

    private static final int REDUCE = -1;
    private static final int PLUS = 1;

    @Override
    protected void createTable(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS shoppingcart ( " +
                "  shoppingCartId integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "  userId varchar(255)  NOT NULL, " +
                "  shopId varchar(255)  NOT NULL, " +
                "  goodsId varchar(255)  NOT NULL, " +
                "  goodsName varchar(255)  NOT NULL, " +
                "  goodsPrice decimal(10, 2) NOT NULL, " +
                "  goodsImgUrl varchar(255)  DEFAULT NULL, " +
                "  goodsCount integer NOT NULL" +
                ") ";
        db.execSQL(sql);
    }


    /**
     * 通过用户、商家 查询购物车信息
     *
     * @return
     */
    @Override
    public List<ShoppingCart> queryByGoodsByUserIdShopId(String userId, String shopId) {
        String sql = "select * from shoppingcart where userId = ?" + " and shopId = ?";
        return query(sql, userId, shopId);
    }

    @Override
    public ShoppingCart queryOne(ShoppingCart shoppingCart) {
        String sql = "select * from shoppingcart where userId = ?" +
                " and shopId = ?" +
                " and goodsId = ?";
        List<ShoppingCart> query = query(sql, shoppingCart.getUserId(), shoppingCart.getShopId(), shoppingCart.getGoodsId());
        return query.size() == 0 ? null : query.get(0);
    }


    /**
     * 向购物车中添加商品
     * 如果已经有同样的商品，对count++；
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public int insertGoods(ShoppingCart shoppingCart) {
        ShoppingCart goods = queryOne(shoppingCart);
        if (goods == null) {
            return insert(shoppingCart);
        } else {
            return updateGoods(shoppingCart, PLUS);
        }
    }

    /**
     * 删除商品通过用户、商家、商品id
     * 对count进行更新
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public int deleteGoods(ShoppingCart shoppingCart) {
        ShoppingCart goods = queryOne(shoppingCart);
        if (goods == null || goods.getGoodsCount() == 0) {
            return 0;
        } else if (goods.getGoodsCount() == 1) {
            String whereClause = "userId = ? and shopId = ? and goodsId = ?";
            return delete(whereClause, shoppingCart.getUserId(), shoppingCart.getShopId(), shoppingCart.getGoodsId());
        } else {
            return updateGoods(shoppingCart, REDUCE);
        }
    }

    @Override
    public int updateGoods(ShoppingCart shoppingCart, int operate) {
        ShoppingCart goods = queryOne(shoppingCart);
        shoppingCart.setGoodsCount(goods.getGoodsCount().intValue() + operate);
        String whereClause = "userId = ? and shopId = ? and goodsId = ?";
        return update(shoppingCart, whereClause, shoppingCart.getUserId(), shoppingCart.getShopId(), shoppingCart.getGoodsId());
    }

    @Override
    public void clean(String userId, String shopId) {
        delete("userId = ? and shopId = ?", userId, shopId);
    }


}
