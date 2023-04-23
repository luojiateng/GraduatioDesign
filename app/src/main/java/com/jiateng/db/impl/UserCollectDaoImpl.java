package com.jiateng.db.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jiateng.base.BaseSQLiteHelper;
import com.jiateng.config.MainApplicationConfig;
import com.jiateng.db.UserCollectDao;
import com.jiateng.domain.UserCollect;
import com.jiateng.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: UserCollectDao
 * @ProjectName: orderFood
 * @date: 2023/4/18 16:47
 * @author: 骆家腾
 */
public class UserCollectDaoImpl extends BaseSQLiteHelper<UserCollect> implements UserCollectDao {
    private static UserCollectDaoImpl userCollectDao;
    private static final String tableName = "collects";

    public UserCollectDaoImpl(Context context, String tableName, Integer dbVersion) {
        super(context, tableName, dbVersion);
    }

    public static UserCollectDaoImpl getInstance(Context context) {
        if (userCollectDao == null) {
            userCollectDao = new UserCollectDaoImpl(context, tableName, 1);
        }
        SQLiteDatabase writableDatabase = userCollectDao.getWritableDatabase();
        writableDatabase.execSQL("create table if not exists collects(" +
                "userId integer ," +
                "shopId integer)");
        helper = userCollectDao;
        helper.openRWLink();
        return userCollectDao;
    }

    @Override
    public Boolean isUserCollectShop(Integer shopId) {
        int userId = SharedPreferencesUtil.getInt(MainApplicationConfig.getContext(), "userId", -1);
        if (userId != -1) {
            return query("select * from collects where userId = ? and shopId = ?", userId, shopId).size() != 0;
        } else {
            return false;
        }
    }


    @Override
    public void putUserCollectShops(ArrayList<UserCollect> collects) {
        for (UserCollect collect : collects) {
            if (!hasValue(collect)) {
                insert(collect, tableName);
            }
        }
    }

    private Boolean hasValue(UserCollect collect) {
        return isUserCollectShop(collect.getShopId());
    }

    @Override
    public Boolean deleteUserCollectShopByShopId(Integer shopId) {
        int userId = SharedPreferencesUtil.getInt(MainApplicationConfig.getContext(), "userId", -1);
        if (userId != -1) {
            return deleteWithTableName(tableName, "userId = ? and shopId = ?", userId, shopId) != 0;
        } else {
            return false;
        }
    }

    @Override
    public void insertUserCollectShop(Integer shopId) {
        int userId = SharedPreferencesUtil.getInt(MainApplicationConfig.getContext(), "userId", -1);
        if (userId != -1) {
            UserCollect collect = new UserCollect(userId, shopId);
            if (!hasValue(collect)) {
                insert(collect, tableName);
            }
        }
    }

    @Override
    protected void createTable(SQLiteDatabase db) {

    }
}
