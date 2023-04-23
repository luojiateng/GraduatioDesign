package com.jiateng.db;

import com.jiateng.domain.UserCollect;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: UserCollectDao
 * @ProjectName: orderFood
 * @date: 2023/4/18 16:43
 * @author: 骆家腾
 */
public interface UserCollectDao {
    Boolean isUserCollectShop(Integer shopId);

    void putUserCollectShops(ArrayList<UserCollect> collects);

    Boolean deleteUserCollectShopByShopId(Integer shopId);

    void insertUserCollectShop(Integer shopId);

}
