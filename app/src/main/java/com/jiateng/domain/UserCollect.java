package com.jiateng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Title: UserCollect
 * @ProjectName: orderFood
 * @date: 2023/4/18 16:41
 * @author: 骆家腾
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollect {
    private Integer userId;
    private Integer shopId;
}
