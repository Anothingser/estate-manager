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

    private String name;

    private String address;

    private Double area;

    private Integer buildingNum;

    private Integer householdsNum;

    private Integer greenRate;

    private String thumbnail;

    private String developer;

    private String estateCompany;

    private String status;
}
