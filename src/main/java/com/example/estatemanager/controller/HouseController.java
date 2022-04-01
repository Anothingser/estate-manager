package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Building;
import com.example.estatemanager.domain.House;
import com.example.estatemanager.service.HouseService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<House> page=houseService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.HOUSE_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @PostMapping("/AddHouse")
    public Result AddHouse(@RequestBody House house){
        if(houseService.AddHouse(house)==1)
            return new Result(true, StatusCode.OK, MessageConstant.HOUSE_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @PostMapping("/UpdateHouse")
    public Result UpdateHouse(@RequestBody House house){
        if (houseService.UpdateHouse(house))
            return new Result(true, StatusCode.OK,
                    MessageConstant.BUILDING_UPDATE_SUCCESS);
        else
            return new Result(false, StatusCode.ERROR, MessageConstant.SYSTEM_BUSY);
    }

    @PostMapping("/DeleteHouse")
    public Result DeleteHouse(@RequestBody List<Integer> ids){
        Boolean flag=houseService.DeleteHouse(ids);
        return new Result(true,StatusCode.OK,MessageConstant.BUILDING_DELETE_SUCCESS);
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.HOUSE_SEARCH_SUCCESS,houseService.FindById(id));
    }


}
