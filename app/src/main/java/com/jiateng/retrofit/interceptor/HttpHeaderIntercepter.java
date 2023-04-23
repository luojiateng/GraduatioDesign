package com.jiateng.retrofit.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:
 * @Title: HttpHeaderIntercepter
 * @date: 2023/4/3 16:03
 * @author: 骆家腾
 */
public class HttpHeaderIntercepter implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request newRequest = chain.request()
                .newBuilder()
                .removeHeader("User-Agent")
                .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)"
                ).build();
        return chain.proceed(newRequest);
    }
}
