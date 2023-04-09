package com.jiateng.retrofit.interceptor;

import static com.jiateng.config.MainApplicationConfig.getContext;

import com.jiateng.utils.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


//在请求头里添加token的拦截器处理
public class TokenHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        String token = SharedPreferencesUtil.getString(getContext(), "token", "");
        String token =
"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NTRjNzYwMDk3ZTU0NTc0YTY0ZTVmNGJjNDg4N2Y4YiIsInN1YiI6IjMiLCJpc3MiOiJsdW8iLCJpYXQiOjE2ODEwMzIzOTMsImV4cCI6MTY4MzYyNDM5M30.N9348hbHLnL2MCoB5Fs3dWPDTsPq8K0HNTkSQEXMp9Q";
        if (token.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        } else {
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest
                    .newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(updateRequest);
        }
    }
}