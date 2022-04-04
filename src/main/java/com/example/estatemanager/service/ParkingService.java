package com.example.estatemanager.service;

import com.example.estatemanager.domain.Parking;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ParkingService {
    Page<Parking> SearchList(Map searchMap);

    boolean AddParking(Parking parking);

    boolean UpdateParking(Parking parking);

    boolean DeleteParking(List<Integer> ids);

    boolean SetStatus(Integer status, Integer id);

    Parking FindById(Integer id);

    Parking FindParkingByCode(String code);
}
