package com.jiateng.retrofit.domain;

import static com.jiateng.config.RetrofitConfig.BASE_HOST;

import com.jiateng.retrofit.interceptor.HttpHeaderIntercepter;
import com.jiateng.retrofit.interceptor.TokenHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private Retrofit retrofit;
    private static String baseHost = "";

    private RetrofitManager() {
    }


    // 使用单例模式封装
    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                    setBaseHost();
                }
            }
        }
        return retrofitManager;
    }

    private static void setBaseHost() {
        baseHost = BASE_HOST;
    }

    private Retrofit getRetrofit(String url) {
            // 添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 使用OkHttpClient
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new HttpHeaderIntercepter())
                    .addInterceptor(new TokenHeaderInterceptor())
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            // 创建出Retrofit
            retrofit = new Retrofit.Builder()
                    // 使用Gson转换工厂
                    .addConverterFactory(GsonConverterFactory.create())
//                    .baseUrl(url == null ? BASE_URL : url)
                    .baseUrl(baseHost + url + "/")
                    .client(okHttpClient)
                    .build();
        return retrofit;
    }

    public <T> T getApiService(String url, Class<T> service) {
        return getRetrofit(url).create(service);
    }
}
