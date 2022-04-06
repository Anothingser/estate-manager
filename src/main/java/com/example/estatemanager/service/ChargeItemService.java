package com.example.estatemanager.service;

import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.domain.Device;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ChargeItemService {
    Page<ChargeItem> SearchList(Map searchMap);

    boolean AddChargeItem(ChargeItem chargeItem);

    boolean UpdateChargeItem(ChargeItem chargeItem);

    boolean DeleteChargeItem(List<Integer> ids);

    ChargeItem FindById(Integer id);

    ChargeItem FindByCode(String code);
}
