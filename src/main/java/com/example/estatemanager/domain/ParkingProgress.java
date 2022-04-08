package com.example.estatemanager.domain;

import lombok.Data;

/**
 * 返回的停车率的实体类
 */
@Data
public class ParkingProgress {
    /**
     * 对应社区名字
     */
    private String communityName;

    /**
     * 停车百分比
     */
    private Integer progress;

    /**
     * 前端进度条应该显示的颜色对应的status
     */
    private String status;
}
