package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="account")
public class Account extends BaseDomain implements Serializable {
    private String username;

    private String password;

    private Integer type;

    private Integer ownerId;
}
