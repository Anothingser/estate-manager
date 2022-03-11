package com.example.estatemanager.service;

import com.example.estatemanager.domain.Community;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CommunityService {

    public List<Community> communityTable();

    public Page<Community> SearchList(Map searchMap);
}
