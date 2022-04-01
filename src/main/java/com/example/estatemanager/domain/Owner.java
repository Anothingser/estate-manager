package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="owner")
public class Owner extends BaseDomain implements Serializable {

    private Integer communityId;

    private String communityName;

    private Integer HouseId;

    private String name;

    private String picture;

    private String idcard;

    private String telephone;

    private String profession;

    private String gender;

    private Integer type;

    private String remark;

    private Date birthday;

    private Integer danger;
}
