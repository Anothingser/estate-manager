package com.example.estatemanager.dao;

import com.example.estatemanager.domain.ChargeItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChargeItemMapper extends Mapper<ChargeItem> {
}
