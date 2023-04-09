package com.jiateng.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @Description:
 * @Title: PicassoUtil
 * @ProjectName: orderFood
 * @date: 2023/2/12 18:28
 * @author: 骆家腾
 */
public class PicassoUtil {
    public static void setImage(String url, ImageView image) {
        Picasso.get().load(url).into(image);
    }
}
