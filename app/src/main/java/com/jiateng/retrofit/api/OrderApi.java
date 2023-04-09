package com.jiateng.retrofit.api;

import com.jiateng.domain.Order;
import com.jiateng.retrofit.domain.ResponseResult;

import org.androidannotations.annotations.rest.Get;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * @Description:
 * @Title: OrderApi
 * @ProjectName: orderFood
 * @date: 2023/1/18 1:09
 * @author: 骆家腾
 */
public interface OrderApi {

    @Get("order")
    Call<ResponseResult<Order>> getHistoryOrderByUserId(@Query("id") String id);
}
