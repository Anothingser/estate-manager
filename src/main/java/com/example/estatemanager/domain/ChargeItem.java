package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="charge_item")
public class ChargeItem extends BaseDomain implements Serializable {
    /**
     * 小区编号
     */
    private Integer communityId;

    /**
     * 小区名字
     */
    private String communityName;

    /**
     * 收费项目编号
     */
    private String code;

    /**
     * 收费项目名称
     */
    private String name;

    /**
     * 收费价格
     */
    private Double money;
}
