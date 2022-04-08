package com.example.estatemanager.domain;

import lombok.Data;

@Data
public class TotalCount {
    /**
     * 总栋数
     */
    private Integer totalBuildings;

    /**
     * 总社区数
     */
    private Integer totalCommunities;

    /**
     * 总户数
     */
    private Integer totalHouses;

    /**
     * 总人数
     */
    private Integer totalPeople;

    /**
     * 租户数量
     */
    private Integer totalTenant;
}
