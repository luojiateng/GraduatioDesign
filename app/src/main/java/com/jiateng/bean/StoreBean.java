package com.jiateng.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Title: StoreBean
 * @date: 2023/1/15 16:58
 * @author: 骆家腾
 */
public class StoreBean implements Serializable {
    public List<Category> categories;
    public List<Goods> goodsList;
    /**
     *  HashMap<Category, List<Goods>> map;
     *  for(map.put(goods.getCategory,new ArrayList<Goods>()))
     *  map.get(goods.getCategory()).add(Goods);
     */


    /**
     * 左侧分类标签
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category implements Serializable {
        private String category;

    }

    /**
     * 右侧商品标签
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Goods implements Serializable {
        private String goodsId;
        private String goodsImgUrl;
        private String name;
        private String category;
        private Double price;
        private Integer count;
    }
}
