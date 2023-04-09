package com.jiateng.domain;

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
    public static class Goods implements Serializable {
        private Integer goodsId;
        private Shop shop;
        private String goodsImageUrl;
        private String goodName;
        private String category;
        private Double price;
        private Integer count;

        public Goods(Integer goodsId) {
            this.goodsId = goodsId;
        }

        @Override
        public String toString() {
            return "Goods{" +
                    "goodsId=" + goodsId +
                    ", shop=" + shop +
                    ", goodsImageUrl='" + goodsImageUrl + '\'' +
                    ", goodName='" + goodName + '\'' +
                    ", category='" + category + '\'' +
                    ", price=" + price +
                    ", count='" + count + '\'' +
                    '}';
        }

        public Goods(Integer goodsId, Shop shop, String goodsImageUrl, String goodName, String category, Double price, Integer count) {
            this.goodsId = goodsId;
            this.shop = shop;
            this.goodsImageUrl = goodsImageUrl;
            this.goodName = goodName;
            this.category = category;
            this.price = price;
            this.count = count;
        }

        public Integer getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(Integer goodsId) {
            this.goodsId = goodsId;
        }

        public Shop getShop() {
            return shop;
        }

        public void setShop(Shop shop) {
            this.shop = shop;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Goods() {
        }
    }
}
