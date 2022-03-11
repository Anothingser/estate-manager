package com.example.estatemanager.controller;


import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;

/**
 *
 * 小区的Contrller层
 */

@RestController
@RequestMapping("/backend/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @RequestMapping("/CommunityList")
    public List<Community> communityTable(){
        return communityService.communityTable();
    }

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Community> page=communityService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }
}
