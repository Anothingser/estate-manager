package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 此类为基类
 */

@Data
public class BaseDomain implements Serializable {

    @Id
    private Integer id;

    private Date createTime;

    private Date updateTime;
}
