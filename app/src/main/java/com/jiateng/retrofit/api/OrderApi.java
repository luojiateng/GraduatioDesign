package com.jiateng.retrofit.api;

import com.jiateng.domain.Order;
import com.jiateng.retrofit.domain.ResponseResult;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Description:
 * @Title: OrderApi
 * @ProjectName: orderFood
 * @date: 2023/1/18 1:09
 * @author: 骆家腾
 */
public interface OrderApi {

    @GET("order")
    Call<ResponseResult<Order>> getHistoryOrderByUserId(@Query("id") String id);

    @POST("insert")
    Call<ResponseBody> createOrder(@Body Order order);

    @GET("get")
    Call<ResponseResult<ArrayList<Order>>> getCurrentUserAllOrders();

    @GET("status/{orderId}")
    Call<ResponseResult<String>> getOrderStatus(@Path("orderId") Integer orderId);

    @DELETE("delete/{orderId}")
    Call<ResponseBody> deleteOrder(@Path("orderId") Integer orderId);

    @PUT("finish/{orderId}")
    Call<ResponseBody> finishOrder(@Path("orderId") Integer orderId);

    @GET("current/{orderId}")
    Call<ResponseResult<Order>> getCurrentOrderInfo(@Path("orderId") Integer orderId);
}
