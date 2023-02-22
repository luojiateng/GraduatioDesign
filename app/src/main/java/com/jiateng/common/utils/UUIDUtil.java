package com.jiateng.common.utils;

import java.util.UUID;

/**
 * @Description:
 * @Title: UUID
 * @ProjectName: orderFood
 * @date: 2023/2/9 20:35
 * @author: 骆家腾
 */
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
