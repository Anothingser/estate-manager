package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * 小区数据库映射类
 */

@Data
@Table(name="community")
public class Community extends BaseDomain implements Serializable {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getHouseholdsNum() {
        return householdsNum;
    }

    public void setHouseholdsNum(int householdsNum) {
        this.householdsNum = householdsNum;
    }

    public int getGreenRate() {
        return greenRate;
    }

    public void setGreenRate(int greenRate) {
        this.greenRate = greenRate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getEstateCompany() {
        return estateCompany;
    }

    public void setEstateCompany(String estateCompany) {
        this.estateCompany = estateCompany;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    private String name;

    private String address;

    private double area;

    private int buildingNum;

    private int householdsNum;

    private int greenRate;

    private String thumbnail;

    private String developer;

    private String estateCompany;

    private char status;
}
