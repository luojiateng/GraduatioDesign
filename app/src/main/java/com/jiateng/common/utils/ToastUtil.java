package com.jiateng.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Description:
 * @Title: ToastUtil
 * @ProjectName: orderFood
 * @date: 2023/2/9 1:07
 * @author: 骆家腾
 */
public class ToastUtil {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
