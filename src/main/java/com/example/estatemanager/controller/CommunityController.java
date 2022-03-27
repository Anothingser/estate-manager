package com.example.estatemanager.controller;


import com.alibaba.fastjson.JSON;
import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.HashMap;
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
        return new PageResult(true, StatusCode.OK,
                MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @PostMapping("AddCommunity")
    public Result AddCommunity(@RequestBody Community community) {
         if (communityService.AddCommunity(community))
            return new Result(true, StatusCode.OK,
                    MessageConstant.COMMUNITY_ADD_SUCCESS);
        else
            return new Result(false, StatusCode.ERROR, MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,communityService.FindById(id));
    }

    @PostMapping("UpdateCommunity")
    public Result UpdateCommunity(@RequestBody Community community) {
        if (communityService.UpdateCommunity(community))
            return new Result(true, StatusCode.OK,
                    MessageConstant.COMMUNITY_UPDATE_SUCCESS);
        else
            return new Result(false, StatusCode.ERROR, MessageConstant.SYSTEM_BUSY);
    }

    @PostMapping("UpdateStatus/{id}/{status}")
    public Result UpdateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status){
        Boolean flag=communityService.UpdateStatus(status,id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }

    @PostMapping("DeleteCommunity")
    public Result DeleteCommunity(@RequestBody List<Integer> ids){
        Boolean flag=communityService.DeleteCommunity(ids);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }

    @RequestMapping ("/GetCommunities") //此方法没用
    public Result GetCommunities(){
        ArrayList communities=communityService.GetCommunities();
        ArrayList list=new ArrayList();
        return new Result();
    }
}
