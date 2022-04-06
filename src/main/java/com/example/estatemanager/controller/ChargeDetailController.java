package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.ChargeDetail;
import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.service.ChargeDetailService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/chargedetail")
public class ChargeDetailController {
    @Autowired
    private ChargeDetailService chargeDetailService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<ChargeDetail> page=chargeDetailService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.CHARGEDETAIL_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("AddChargeDetail")
    public Result AddChargeDetail(@RequestBody ChargeDetail chargeDetail){
        if(chargeDetailService.AddChargeDetail(chargeDetail))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("UpdateChargeDetail")
    public Result UpdateChargeDetail(@RequestBody ChargeDetail chargeDetail){
        if(chargeDetailService.UpdateChargeDetail(chargeDetail))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("DeleteChargeDetail")
    public Result DeleteChargeDetail(@RequestBody List<Integer> ids){
        if(chargeDetailService.DeleteChargeDetail(ids))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS,chargeDetailService.FindById(id));
    }


}
