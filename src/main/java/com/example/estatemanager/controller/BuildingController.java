package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Building;
import com.example.estatemanager.service.BuildingService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 楼栋Controller
 */
@RestController
@RequestMapping("/backend/building")
public class BuildingController {

    @Autowired
    public BuildingService buildingService;

    @RequestMapping("GetBuildingList")
    public List<Building> GetBuildingList(){
        return buildingService.GetBuildingList();
    }

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Building> page=buildingService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.BUILDING_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @PostMapping("/AddBuilding")
    public Result AddBuilding(@RequestBody Building building){
        if(buildingService.AddBuilding(building)==1)
            return new Result(true, StatusCode.OK, MessageConstant.BUILDING_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @PostMapping("/DeleteBuilding")
    public Result DeleteBuilding(@RequestBody List<Integer> ids){
        Boolean flag=buildingService.DeleteBuilding(ids);
        return new Result(true,StatusCode.OK,MessageConstant.BUILDING_DELETE_SUCCESS);
    }

    @PostMapping("/UpdateBuilding")
    public Result UpdateBuilding(@RequestBody Building building){
        if (buildingService.UpdateBuilding(building))
            return new Result(true, StatusCode.OK,
                    MessageConstant.BUILDING_UPDATE_SUCCESS);
        else
            return new Result(false, StatusCode.ERROR, MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,buildingService.FindById(id));
    }
}
