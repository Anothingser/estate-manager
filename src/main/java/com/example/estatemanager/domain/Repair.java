package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="repair")
public class Repair extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private Integer buildingId;

    private String buildingName;

    private Integer ownerId;

    private String ownerName;

    private Integer deviceId;

    private String deviceName;

    private String description;

    private Integer status;
}
