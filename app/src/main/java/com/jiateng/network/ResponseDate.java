package com.jiateng.network;

import lombok.Data;

/**
 * @Description:
 * @Title: ResponseDate
 * @ProjectName: orderFood
 * @date: 2023/1/18 1:04
 * @author: 骆家腾
 */
@Data
public class ResponseDate<T> {
    private Integer code;
    private String message;
    private T data;
}
