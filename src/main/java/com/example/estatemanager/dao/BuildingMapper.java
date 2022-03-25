package com.example.estatemanager.dao;

import com.example.estatemanager.domain.Building;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BuildingMapper extends Mapper<Building> {

}
