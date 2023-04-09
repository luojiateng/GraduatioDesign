package com.jiateng.utils;

import android.widget.Toast;

import com.jiateng.config.MainApplicationConfig;

/**
 * @Description:
 * @Title: ToastUtil
 * @ProjectName: orderFood
 * @date: 2023/2/9 1:07
 * @author: 骆家腾
 */
public class ToastUtil {
    public static void ToastShow(String msg) {
        Toast.makeText(MainApplicationConfig.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
