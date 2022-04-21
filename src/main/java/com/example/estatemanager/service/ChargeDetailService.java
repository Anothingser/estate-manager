package com.example.estatemanager.service;

import com.example.estatemanager.domain.ChargeDetail;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ChargeDetailService {
    Page<ChargeDetail> SearchList(Map searchMap);

    boolean AddChargeDetail(ChargeDetail chargeDetail);

    boolean UpdateChargeDetail(ChargeDetail chargeDetail);

    boolean DeleteChargeDetail(List<Integer> ids);

    ChargeDetail FindById(Integer id);

    boolean EndOrder(Integer id);

    boolean SendCharges(Integer id,Integer sendingFlag);

    boolean SendSomeCharge(Integer id, List<Integer> ids);
}
