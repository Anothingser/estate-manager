package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.House;
import com.example.estatemanager.domain.Repair;
import com.example.estatemanager.service.RepairService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/repair")
public class RepairController {
    @Autowired
    private RepairService repairService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Repair> page=repairService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.REPAIR_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/AddRepair")
    public Result AddRepair(@RequestBody Repair repair){
        if(repairService.AddRepair(repair))
            return new Result(true, StatusCode.OK, MessageConstant.REPAIR_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateRepair")
    public Result UpdateRepair(@RequestBody Repair repair){
        if(repairService.UpdateRepair(repair))
            return new Result(true, StatusCode.OK, MessageConstant.REPAIR_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/DeleteRepair")
    public Result DeleteRepair(@RequestBody List<Integer> ids){
        if(repairService.DeleteRepair(ids))
            return new Result(true, StatusCode.OK, MessageConstant.REPAIR_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/NextStep")
    public Result NextStep(Integer id, Integer status){
        if(repairService.NextStep(id,status))
            return new Result(true, StatusCode.OK, MessageConstant.OPERATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }
}
