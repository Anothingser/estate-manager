package com.example.estatemanager.service;

import com.example.estatemanager.domain.ParkingUse;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ParkingUseService {
    Page<ParkingUse> SearchList(Map searchMap);

    boolean AddParkingUse(ParkingUse parkingUse);

    boolean UpdateParkingUse(ParkingUse parkingUse);

    boolean DeleteParkingUse(List<Integer> ids);

    boolean FinishiParking(Integer id);
}
