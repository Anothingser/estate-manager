package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.domain.Device;
import com.example.estatemanager.service.ChargeItemService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/chargeitem")
public class ChargeItemController {
    @Autowired
    private ChargeItemService chargeItemService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<ChargeItem> page=chargeItemService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.CHARGEITEM_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/AddChargeItem")
    public Result AddChargeItem(@RequestBody ChargeItem chargeItem){
        if(chargeItemService.AddChargeItem(chargeItem))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateChargeItem")
    public Result UpdateChargeItem(@RequestBody ChargeItem chargeItem){
        if(chargeItemService.UpdateChargeItem(chargeItem))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("DeleteChargeItem")
    public Result DeleteChargeItem(@RequestBody List<Integer> ids){
        if(chargeItemService.DeleteChargeItem(ids))
            return new Result(true,StatusCode.OK,MessageConstant.CHARGEDETAIL_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS,chargeItemService.FindById(id));
    }
}
