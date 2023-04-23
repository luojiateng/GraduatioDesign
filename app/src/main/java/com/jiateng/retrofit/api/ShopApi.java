package com.jiateng.retrofit.api;

import com.jiateng.domain.Shop;
import com.jiateng.domain.StoreBean;
import com.jiateng.retrofit.domain.ResponseResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Description:
 * @Title: ShopApi
 * @ProjectName: orderFood
 * @date: 2023/4/8 16:48
 * @author: 骆家腾
 */
public interface ShopApi {
    @GET("all")
    Call<ResponseResult<ArrayList<Shop>>> getShopList();

    @GET("current/{shopId}")
    Call<ResponseResult<ArrayList<StoreBean.Goods>>> getShopGoods(@Path("shopId") Integer shopId);

    @GET("search/{key}")
    Call<ResponseResult<ArrayList<Shop>>> searchShop(@Path("key") String key);

}
