package com.example.estatemanager.service;

import com.example.estatemanager.domain.Repair;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface RepairService {
    Page<Repair> SearchList(Map searchMap);

    boolean AddRepair(Repair repair);

    boolean UpdateRepair(Repair repair);

    boolean DeleteRepair(List<Integer> ids);

    boolean NextStep(Integer id, Integer status);

    List<Repair> GetTen();
}
