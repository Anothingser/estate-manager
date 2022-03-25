package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="building")
public class Building extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private String name;

    private Integer householdsNum;

    private String description;

    private Integer danger;
}
