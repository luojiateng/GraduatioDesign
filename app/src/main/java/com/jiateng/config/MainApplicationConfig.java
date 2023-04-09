package com.jiateng.config;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * xUtils框架注入
 */
public class MainApplicationConfig extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xUtils初始化
        context = getApplicationContext();
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }
}
