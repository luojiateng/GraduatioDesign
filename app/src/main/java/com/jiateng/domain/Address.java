package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: Address
 * @ProjectName: orderFood
 * @date: 2023/1/15 16:59
 * @author: 骆家腾
 */
public class Address implements Serializable {
    private Integer addressId;
    private String province;
    private String city;
    private String county;
    private String schoolName;
    private String build;
    private String specificAddress;
    private Boolean isFirst;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    @Override
    public String toString() {
        String addresss = "Address{" +
                "addressId='" + addressId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", build='" + build + '\'' +
                ", specificAddress='" + specificAddress + '\'' +
                ", isFirst=" + isFirst +
                '}';

        return build + " " + specificAddress;
    }

    public Address() {
    }

    public Address(Integer addressId, String province, String city, String county, String schoolName, String build, String specificAddress, Boolean isFirst) {
        this.addressId = addressId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.schoolName = schoolName;
        this.build = build;
        this.specificAddress = specificAddress;
        this.isFirst = isFirst;
    }
}
