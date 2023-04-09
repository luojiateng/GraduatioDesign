package com.jiateng.retrofit.domain;

import com.alibaba.fastjson2.JSON;
import com.jiateng.utils.ToastUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @Description:
 * @Title: ResponseUtil
 * @ProjectName: orderfoodDemo
 * @date: 2023/4/3 15:41
 * @author: 骆家腾
 */
public class ResultUtil {

    public static <T> T getResult(Response<ResponseResult<T>> response) {
        ResponseResult<T> body = response.body();
        if (body.getCode() == 200 || body.getCode() == 201) {
            return body.getData();
        } else if (body.getCode() == 401 || body.getCode() == 403) {
            ToastUtil.ToastShow("请登录");
            return null;
        } else {
            return null;
        }
    }

    public static String getResultMsg(Response<ResponseBody> response) {
        String json = null;
        try {
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseResult responseResult = JSON.parseObject(json, ResponseResult.class);
        return responseResult.getMsg();
    }
}
