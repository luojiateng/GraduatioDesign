package com.jiateng.api;

import com.jiateng.common.bean.ResponseDate;
import com.jiateng.bean.Order;

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
    Call<ResponseDate<Order>> getHistoryOrderByUserId(@Query("id") String id);
}
