package com.example.estatemanager.dao;

import com.example.estatemanager.domain.Car;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CarMapper extends Mapper<Car> {
}
