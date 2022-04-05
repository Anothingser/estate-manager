package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Device;
import com.example.estatemanager.domain.Parking;
import com.example.estatemanager.service.DeviceService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 设备
 */
@RestController
@RequestMapping("/backend/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;


    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Device> page=deviceService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.DEVICE_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/AddDevice")
    public Result AddDevice(@RequestBody Device device){
        if(deviceService.AddDevice(device))
            return new Result(true,StatusCode.OK,MessageConstant.DEVICE_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateDevice")
    public Result UpdateDevice(@RequestBody Device device){
        if(deviceService.UpdateDevice(device))
            return new Result(true,StatusCode.OK,MessageConstant.DEVICE_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/DeleteDevice")
    public Result DeleteDevice(@RequestBody List<Integer> ids){
        if(deviceService.DeleteDevice(ids))
            return new Result(true,StatusCode.OK,MessageConstant.DEVICE_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.OPERATE_SUCCESS,deviceService.FindById(id));
    }

}
