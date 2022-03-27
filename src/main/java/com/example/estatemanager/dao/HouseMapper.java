package com.example.estatemanager.dao;


import com.example.estatemanager.domain.House;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface HouseMapper extends Mapper<House> {

}
