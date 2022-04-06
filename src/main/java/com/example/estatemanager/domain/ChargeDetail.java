package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="charge_detail")
public class ChargeDetail extends BaseDomain implements Serializable {
    private Integer communityId;

    private String communityName;

    private Integer chargeItemId;

    private String chargeItemName;

    private String contractor;

    private String telephone;

    private Double pay;

    private Double collection;

    private Date payTime;

    private Integer status;
}
