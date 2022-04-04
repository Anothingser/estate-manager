package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="parking_use")
public class ParkingUse extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private String code;

    private String carNumber;

    private String picture;

    private Integer ownerId;

    private String ownerName;

    private String telephone;

    private Integer useType;

    private Integer totalFee;

    private Date startTime;

    private Date endTime;
}
