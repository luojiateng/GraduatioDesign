package com.jiateng.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description:
 * @Title: Record
 * @ProjectName: MyApp
 * @date: 2023/2/10 20:31
 * @author: 骆家腾
 */
@Data
public class Record implements Serializable {
    private Integer id;
    private String name;
}
