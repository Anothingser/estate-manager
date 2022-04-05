package com.example.estatemanager.dao;

import com.example.estatemanager.domain.Complaint;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ComplaintMapper extends Mapper<Complaint> {
}
