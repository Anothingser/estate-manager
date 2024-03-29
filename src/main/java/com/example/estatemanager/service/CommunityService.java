package com.example.estatemanager.service;

import com.example.estatemanager.domain.Community;
import com.example.estatemanager.domain.ParkingProgress;
import com.example.estatemanager.domain.TotalCount;
import com.example.estatemanager.service.impl.CommunityServiceImpl;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommunityService {

    public List<Community> communityTable();

    public Page<Community> SearchList(Map searchMap);

    boolean AddCommunity(Community community);

    public Community FindById(Integer id);

    boolean UpdateCommunity(Community community);

    Boolean UpdateStatus(Integer status, Integer id);

    Boolean DeleteCommunity(List<Integer> ids);

    ArrayList GetCommunities();

    TotalCount GetTotals();

    List<ParkingProgress> GetParkingProgress();

    List<String> communityNames();

    List<CommunityServiceImpl.Value> communityValues();

    Integer GetDanger();
}
