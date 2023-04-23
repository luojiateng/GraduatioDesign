package com.jiateng.db.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jiateng.base.BaseSQLiteHelper;
import com.jiateng.config.MainApplicationConfig;
import com.jiateng.db.ShoppingCartDao;
import com.jiateng.domain.ShoppingCart;
import com.jiateng.domain.ShoppingCartDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Title: ShoppingCartDaoA
 * @ProjectName: orderFood
 * @date: 2023/4/9 18:23
 * @author: 骆家腾
 */
public class ShoppingCartDaoImpl extends BaseSQLiteHelper<ShoppingCartDTO> implements ShoppingCartDao {
    private static ShoppingCartDaoImpl shoppingCartDao;
    private static String tableName = "shoppingcart";

    public static ShoppingCartDaoImpl getInstance() {
        if (shoppingCartDao == null) {
            shoppingCartDao = new ShoppingCartDaoImpl(MainApplicationConfig.getContext(), tableName, 1);
        }
        helper = shoppingCartDao;
        SQLiteDatabase writableDatabase = shoppingCartDao.getWritableDatabase();
        writableDatabase.execSQL(" CREATE TABLE IF NOT EXISTS shoppingcart ( " +
                "  shoppingCartId integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "  userId varchar(255)   NOT NULL, " +
                "  shopId varchar(255)   NOT NULL, " +
                "  goodsId varchar(255)   NOT NULL, " +
                "  goodsName varchar(255)  NOT NULL, " +
                "  goodsPrice decimal(10, 2) NOT NULL, " +
                "  goodsImgUrl varchar(255)  DEFAULT NULL, " +
                "  goodsCount integer NOT NULL" +
                ")");
        helper.openRWLink();
        return shoppingCartDao;
    }

    public ShoppingCartDaoImpl(Context context, String tableName, Integer dbVersion) {
        super(context, tableName, dbVersion);
    }

    private static final int REDUCE = -1;
    private static final int PLUS = 1;

    @Override
    protected void createTable(SQLiteDatabase db) {
//        String sql = " CREATE TABLE IF NOT EXISTS shoppingcart ( " +
//                "  shoppingCartId integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                "  userId varchar(255)   NOT NULL, " +
//                "  shopId varchar(255)   NOT NULL, " +
//                "  goodsId varchar(255)   NOT NULL, " +
//                "  goodsName varchar(255)  NOT NULL, " +
//                "  goodsPrice decimal(10, 2) NOT NULL, " +
//                "  goodsImgUrl varchar(255)  DEFAULT NULL, " +
//                "  goodsCount integer NOT NULL" +
//                ")";
//        db.execSQL(sql);
    }

    @Override
    public List<ShoppingCart> queryByGoodsByUserIdShopId(Integer userId, Integer shopId) {
        String sql = "select * from shoppingcart where userId = ?" + " and shopId = ?";
        return query(sql, userId, shopId).stream().map(shoppingCartDTO -> new ShoppingCart(shoppingCartDTO)).collect(Collectors.toList());
    }

    @Override
    public ShoppingCart queryOne(ShoppingCart shoppingCart) {
        String sql = "select * from shoppingcart where userId = ?" +
                " and shopId = ?" +
                " and goodsId = ?";
        List<ShoppingCartDTO> query = query(sql, shoppingCart.getUser().getUserId(), shoppingCart.getShop().getShopId(), shoppingCart.getGoods().getGoodsId());
        return query.size() == 0 ? null : new ShoppingCart(query.get(0));
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
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO(shoppingCart);
        ShoppingCart goods = queryOne(shoppingCart);
        if (goods == null) {
            return insert(shoppingCartDTO, tableName);
        } else {
            return updateGoods(shoppingCart, PLUS);
        }
    }

    @Override
    public int deleteGoods(ShoppingCart shoppingCart) {
        ShoppingCart goods = queryOne(shoppingCart);
        if (goods == null || goods.getGoodsCount() == 0) {
            return 0;
        } else if (goods.getGoodsCount() == 1) {
            String whereClause = "userId = ? and shopId = ? and goodsId = ?";
            return delete(whereClause, shoppingCart.getUser().getUserId(), shoppingCart.getShop().getShopId(), shoppingCart.getGoods().getGoodsId());
        } else {
            return updateGoods(shoppingCart, REDUCE);
        }
    }

    @Override
    public int updateGoods(ShoppingCart shoppingCart, int operate) {
        ShoppingCart goods = queryOne(shoppingCart);
        shoppingCart.setGoodsCount(goods.getGoodsCount().intValue() + operate);
        String whereClause = "userId = ? and shopId = ? and goodsId = ?";
        return update(new ShoppingCartDTO(shoppingCart), whereClause, shoppingCart.getUser().getUserId(), shoppingCart.getShop().getShopId(), shoppingCart.getGoods().getGoodsId());

    }

    @Override
    public void clean(Integer userId, Integer shopId) {
        delete("userId = ? and shopId = ?", userId, shopId);
    }
}
