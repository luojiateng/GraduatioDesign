package com.jiateng.common.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static RetrofitUtils retrofitUtils;
    private static final String BaseUrl = "http://192.168.135.1:80/";

    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        if (retrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    //返回Retrofit
    private static Retrofit getRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url != null ? url : BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public <T> T getApiService(String url, Class<T> service) {

        Retrofit retrofit = getRetrofit(url);
        //通过java动态代理模式获取代理对象
        T t = retrofit.create(service);
        return t;

    }

    public <T> T getApiService(Class<T> service) {
        return getApiService(null, service);

    }
}
