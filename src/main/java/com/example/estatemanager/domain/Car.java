package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="car")
public class Car extends BaseDomain implements Serializable {

    private String picture;

    private Integer ownerId;

    private String ownerName;

    private Integer communityId;

    private String color;

    private String carNumber;

    private String remark;

    private String ifTempory;
}
