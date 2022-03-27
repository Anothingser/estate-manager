package com.example.estatemanager.service;

import com.example.estatemanager.domain.House;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface HouseService {
    Page<House> SearchList(Map searchMap);

    House FindById(Integer id);

    int AddHouse(House house);

    boolean UpdateHouse(House house);

    Boolean DeleteHouse(List<Integer> ids);
}
