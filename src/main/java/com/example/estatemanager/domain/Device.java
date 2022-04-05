package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="device")
public class Device extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private String code;

    private String name;

    private String brand;

    private Double unitPrice;

    private Integer num;

    private Integer expectAge;

    private Date purchaseDate;

    private Integer status;
}
