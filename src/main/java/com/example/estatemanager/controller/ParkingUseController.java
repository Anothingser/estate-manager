package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Parking;
import com.example.estatemanager.domain.ParkingUse;
import com.example.estatemanager.service.ParkingUseService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/parkinguse")
public class ParkingUseController {

    @Autowired
    private ParkingUseService parkingUseService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<ParkingUse> page=parkingUseService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.PARKINGUSE_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/AddParkingUse")
    public Result AddParkingUse(@RequestBody ParkingUse parkingUse){
        if(parkingUseService.AddParkingUse(parkingUse))
            return new Result(true,StatusCode.OK,MessageConstant.PARKINGUSE_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateParkingUse")
    public Result UpdateParkingUse(@RequestBody ParkingUse parkingUse){
        if(parkingUseService.UpdateParkingUse(parkingUse))
            return new Result(true,StatusCode.OK,MessageConstant.PARKINGUSE_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("DeleteParkingUse")
    public Result DeleteParkingUse(@RequestBody List<Integer> ids){
        if(parkingUseService.DeleteParkingUse(ids)){
            return new Result(true,StatusCode.OK,MessageConstant.PARKINGUSE_DELETE_SUCCESS);
        }else{
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
        }
    }

    @RequestMapping("Finishi")
    public Result FinishiParking(Integer id){
        if(parkingUseService.FinishiParking(id))
            return new Result(true,StatusCode.OK,MessageConstant.PARKINGUSE_FINISH_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }
}
