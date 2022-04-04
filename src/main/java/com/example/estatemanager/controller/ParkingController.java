package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Parking;
import com.example.estatemanager.service.ParkingService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Parking> page=parkingService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.PARKING_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/AddParking")
    public Result AddParking(@RequestBody Parking parking){
        if(parkingService.AddParking(parking))
            return new Result(true,StatusCode.OK,MessageConstant.PARKING_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateParking")
    public Result UpdateParking(@RequestBody Parking parking){
        if(parkingService.UpdateParking(parking))
            return new Result(true,StatusCode.OK,MessageConstant.PARKING_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }


    @RequestMapping("/DeleteParking")
    public Result DeleteParking(@RequestBody List<Integer> ids){
        if(parkingService.DeleteParking(ids))
            return new Result(true,StatusCode.OK,MessageConstant.PARKING_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.OPERATE_SUCCESS,parkingService.FindById(id));
    }

    @PostMapping("SetStatus/{id}/{status}")
    public Result SetStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status){
        if(parkingService.SetStatus(status,id))
            return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/FindParkingByCode")
    public Result FindParkingByCode(String code){
        if(parkingService.FindParkingByCode(code)!=null)
            return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS,parkingService.FindParkingByCode(code));
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.PARKING_FINDBYCODE_ERROR);
    }


}
