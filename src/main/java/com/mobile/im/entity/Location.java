package com.mobile.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobile.im.annotation.Id;
import com.mobile.im.annotation.IgnoreColumn;
import com.mobile.im.start.event.MsgEvent;

import java.util.Date;

public class Location {
    @Id
    private int id;
    private String username;
    private String lat;
    private String lng;
    private String radius;
    private String countryCode;
    private String country;
    private String cityCode;
    private String city;
    private String district;
    private String street;
    private String addr;
    private String extMsg;
    private Date update_time;
    @IgnoreColumn
    private int type;
    @IgnoreColumn
    private String to;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getExtMsg() {
        return extMsg;
    }

    public void setExtMsg(String extMsg) {
        this.extMsg = extMsg;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
