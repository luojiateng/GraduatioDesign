package com.jiateng.common.config;

import android.app.Application;

import org.xutils.x;

/**
 * xUtils框架注入
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xUtils初始化
    }
}
