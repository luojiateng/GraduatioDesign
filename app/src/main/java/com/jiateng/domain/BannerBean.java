package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: BannerBean
 * @ProjectName: orderFood
 * @date: 2023/2/12 0:44
 * @author: 骆家腾
 */
public class BannerBean  implements Serializable {
    private Integer bannerId;
    private String bannerImgUrl;
    private String createTime;

    @Override
    public String toString() {
        return "BannerBean{" +
                "bannerId=" + bannerId +
                ", bannerImgUrl='" + bannerImgUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BannerBean() {
    }

    public BannerBean(Integer bannerId, String bannerImgUrl, String createTime) {
        this.bannerId = bannerId;
        this.bannerImgUrl = bannerImgUrl;
        this.createTime = createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
