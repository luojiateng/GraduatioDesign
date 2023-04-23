package com.jiateng.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Title: AddressInfo
 * @ProjectName: orderFood
 * @date: 2023/4/14 19:42
 * @author: 骆家腾
 */
public class AddressInfo implements Serializable {
    private String userName;
    private String userPhone;
    protected Integer addressId;
    protected String province;
    protected String city;
    protected String county;
    protected String schoolName;
    protected String build;
    protected String specificAddress;
    protected Boolean isFirst;

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
                "name='" + userName + '\'' +
                ", phone='" + userPhone + '\'' +
                '}';

        return build + specificAddress;
    }

    public String getAddressInfo() {
        return schoolName + build + specificAddress;
    }


    public AddressInfo(Integer addressId, String schoolStr, String buildStr, String homeIdStr, String usernameStr, String phoneNumberStr) {
        this.addressId = addressId;
        this.schoolName = schoolStr;
        this.build = buildStr;
        this.specificAddress = homeIdStr;
        this.userName = usernameStr;
        this.userPhone = phoneNumberStr;
    }

    public AddressInfo() {
    }

    public AddressInfo(Integer addressId) {
        this.addressId = addressId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
