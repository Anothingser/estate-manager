package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="house")
public class House extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private Integer buildingId;

    private String buildingName;

    private String code;

    private String name;

    private Integer ownerId;

    private String ownerName;

    private String telephone;

    private Integer roomNum;

    private Integer unit;

    private Integer floor;

    private String description;

    private Date liveTime;

    private Integer danger;

}
