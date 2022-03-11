package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

/**
 * 此类为基类
 */

@Data
public class BaseDomain implements Serializable {

    @Id
    private int id;

    private Date CreateTime;

    private Date UpdateTime;
}
