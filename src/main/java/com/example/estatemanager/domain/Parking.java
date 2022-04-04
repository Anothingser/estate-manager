package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="parking")
public class Parking extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private String picture;

    private String code;

    private String name;

    private Integer status;
}
