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
        String token = SharedPreferencesUtil.getString(getContext(), "token", "");
//        String token =
//"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1ZTkzOWRiZjg5MDI0ZTBlYTllYTYxNGJjM2IzZDIwZSIsInN1YiI6IjEiLCJpc3MiOiJsdW8iLCJpYXQiOjE2ODE2NTA3NDgsImV4cCI6MTY4NDI0Mjc0OH0.FqvakQsOowpArFK9qT7K4KENEjBmSOVp0ge4kqi8KHQ";
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