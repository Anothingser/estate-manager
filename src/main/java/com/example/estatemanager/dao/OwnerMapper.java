package com.example.estatemanager.dao;

import com.example.estatemanager.domain.Owner;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OwnerMapper extends Mapper<Owner> {
}
