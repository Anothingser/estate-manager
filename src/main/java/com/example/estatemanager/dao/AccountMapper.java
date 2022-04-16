package com.example.estatemanager.dao;

import com.example.estatemanager.domain.Account;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AccountMapper extends Mapper<Account>{
}
