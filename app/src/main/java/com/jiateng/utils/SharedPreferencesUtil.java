package com.jiateng.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiateng.config.MainApplicationConfig;
import com.jiateng.domain.User;
import com.jiateng.domain.UserDTO;


public class SharedPreferencesUtil {
    private static SharedPreferences sharedPreferences;

    /**
     * 写入Boolean变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putBoolean(Context context, String key, Boolean value) {
        //(存储节点文件名称，读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取boolean标识从sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   没有此节点的默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context, String key, Boolean value) {
        //(存储节点文件名称,读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * 写入String变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putString(Context context, String key, String value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }


    /**
     * 读取String标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static String getString(Context context, String key, String defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * 写入int变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putInt(Context context, String key, int value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }


    /**
     * 读取int标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static int getInt(Context context, String key, int defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * 从sharedPreferences中移除指定节点
     *
     * @param context 上下文环境
     * @param key     需要移除节点的名称
     */
    public static void remove(Context context, String key) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).commit();
    }

    public static void putUserInfo(User user) {
        Context context = MainApplicationConfig.getContext();
        putInt(context, "userId", user.getUserId());
        putString(context, "nickName", user.getNickName());
        putString(context, "avatar", user.getAvatarUrl());
        putString(context, "phoneNumber", user.getPhoneNumber());
        putString(context, "username", user.getUsername());
        putString(context, "sex", user.getSex());
    }

    public static User getUser() {
        Context context = MainApplicationConfig.getContext();
        int userId = getInt(context, "userId", -1);
        String nickName = getString(context, "nickName", "null");
        String avatarUrl = getString(context, "avatar", "null");
        String password = getString(context, "password", "null");
        String phoneNumber = getString(context, "phoneNumber", "null");
        String username = getString(context, "username", "null");
        String sex = getString(context, "sex", "null");
        return new User(userId, nickName, phoneNumber, password, avatarUrl, username, sex);
    }

    public static void putUserBaseInfo(UserDTO userDTO) {
        Context context = MainApplicationConfig.getContext();
        putInt(context, "userId", userDTO.getUserId());
        putString(context, "token", userDTO.getToken());
        putString(context, "phoneNumber", userDTO.getPhoneNumber());
        putString(context, "nickName", userDTO.getNickName());
    }

    public static void removeUserBaseInfo() {
        Context context = MainApplicationConfig.getContext();
        remove(context, "userId");
        remove(context, "phoneNumber");
        remove(context, "nickName");
        remove(context, "avatar");
        remove(context, "phoneNumber");
        remove(context, "token");
        remove(context, "sex");
    }

    public static Boolean hasToken() {
        Context context = MainApplicationConfig.getContext();
        return !"null".equals(getToken());
    }

    public static String getToken() {
        Context context = MainApplicationConfig.getContext();
        return getString(context, "token", "null");
    }
}

