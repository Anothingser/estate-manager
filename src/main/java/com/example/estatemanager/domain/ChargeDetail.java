package com.example.estatemanager.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="charge_detail")
public class ChargeDetail extends BaseDomain implements Serializable {
}
