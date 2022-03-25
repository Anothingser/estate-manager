package com.example.estatemanager.service;

import com.example.estatemanager.domain.Building;
import com.example.estatemanager.domain.Community;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<Building> GetBuildingList();

    int AddBuilding(Building building);

    Boolean DeleteBuilding(List<Integer> ids);

    Page<Building> SearchList(Map searchMap);

    boolean UpdateBuilding(Building building);
}
