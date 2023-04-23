package com.jiateng.retrofit.api;

import com.jiateng.domain.Shop;
import com.jiateng.domain.User;
import com.jiateng.domain.UserDTO;
import com.jiateng.retrofit.domain.ResponseResult;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * @Description:
 * @Title: UserApi
 * @ProjectName: orderFood
 * @date: 2023/4/3 14:25
 * @author: 骆家腾
 */
public interface UserApi {

    @GET("userinfo")
    Call<ResponseResult<User>> getUserInfo();

    @POST("login")
    Call<ResponseResult<UserDTO>> loginUser(@Body User user);

    @GET("logout")
    Call<ResponseBody> logout();

    @POST("singup")
    Call<ResponseResult<UserDTO>> singup(@Body User user);

    @GET("favourite/get")
    Call<ResponseResult<ArrayList<Shop>>> getUserFavouriteShops();

    @DELETE("favourite/delete/{shopId}")
    Call<ResponseBody> removeCollect(@Path("shopId") Integer shopId);

    @POST("favourite/set/{shopId}")
    Call<ResponseBody> insertCollect(@Path("shopId") Integer shopId);

    @PUT("update")
    Call<ResponseBody> updateUserInfo(@Body User user);

    @Multipart
    @POST("avatarUpload")
    Call<ResponseBody> uploadUserAvatar(@Part MultipartBody.Part file);

}
