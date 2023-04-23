package com.jiateng.retrofit.api;

import com.jiateng.domain.Address;
import com.jiateng.domain.AddressInfo;
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

/**
 * @Description:
 * @Title: AddressApi
 * @ProjectName: orderFood
 * @date: 2023/4/14 13:39
 * @author: 骆家腾
 */
public interface AddressApi {
    @GET("shop/get/{shopId}")
    Call<ResponseResult<Address>> getShopAddress(@Path("shopId") Integer shopId);

    @GET("user/get")
    Call<ResponseResult<ArrayList<AddressInfo>>> getUserAddress();

    @POST("user/insert")
    Call<ResponseBody> insertUserAddress(@Body AddressInfo addressInfo);

    @PUT("user/update")
    Call<ResponseBody> updateUserAddress(@Body AddressInfo addressInfo);

    @DELETE("user/delete/{addressId}")
    Call<ResponseBody> deleteAddressInfoByAddressId(@Path("addressId") Integer addressId);
}
