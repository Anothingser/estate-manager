package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="complaint")
public class Complaint extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private Integer ownerId;

    private String ownerName;

    private String description;

    private String reason;

    private Integer status;

}
