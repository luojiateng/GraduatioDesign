package com.jiateng.retrofit.api;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * @Description:
 * @Title: Api
 * @ProjectName: MyApp
 * @date: 2023/1/17 22:41
 * @author: 骆家腾
 */
public interface Api {


    /*@POST("login")
    Call<ResponseResult<UserDTO>> login(@Body User user);

//    @GET("userinfo")
//    Call<ResponseBody> getUserInfo();

    @GET("userinfo")
    Call<ResponseResult<User>> getUserInfo();

    @POST("singup")
    Call<ResponseBody> singup(@Body User user);

    @PUT("update")
    Call<ResponseBody> updateUser(@Body User user);

*/
//    /**
//     * 请求根域名时
//     *
//     * @return
//     */
//    @GET("/")
//    Call<ResponseBody> getFromBaidu();
//
//    /**
//     * get请求
//     */
//    @GET("user")
//    Call<ResponseBody> get();
//
//    /**
//     * 含有参数的get请求
//     *
//     * @param id
//     * @return
//     */
//    @GET("user")
//    Call<ResponseBody> getById(@Query("id") int id);
//
//    /**
//     * //form表单,每个键值对需要使用@Field注解
//     * //如果有参数需要加上FormUrlEncode注解
//     * <p>
//     * //多个参数的情况下，使用map
//     * //Call<ResponseBody> setUserInfo(@FieldMap Map<String, String> map);
//     */
//    @FormUrlEncoded
//    @POST("user/info")
//    Call<ResponseBody> setUserInfo(@Field("id") Integer id, @Field("name") String name);
//
//
//    /**
//     * 上传JSON对象
//     */
//    @POST("user/login")
//    Call<ResponseBody> setUser(@Body User user);
//
//    /**
//     * @Multipart 表示请求实体是一个支持文件上传的表单，需要配合@Part和@PartMap使用，适用于文件上传
//     * @Part 用于表单字段，适用于文件上传的情况，@Part支持三种类型：RequestBody、MultipartBody.Part、                                       任意类型
//     * @PartMap 用于多文件上传， 与@FieldMap和@QueryMap的使用类似
//     */
//    @Multipart
//    @POST
//    Call<ResponseBody> uploadFile(@Part("name") RequestBody name, @Part MultipartBody.Part file);
//
//
//    /**
//     * Example
//     */
//    @GET("api/rand.music")
//    Call<Data<Info>> getJsonData(@Query("sort") String sort, @Query("format") String format);

}
