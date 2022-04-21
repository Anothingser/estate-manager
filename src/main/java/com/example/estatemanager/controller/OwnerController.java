package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.service.OwnerService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Owner> page=ownerService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.OWNER_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @PostMapping("/DeleteOwner")
    public Result DeleteOwner(@RequestBody List<Integer> ids){
        Boolean flag=ownerService.DeleteOwner(ids);
        return new Result(true,StatusCode.OK,MessageConstant.OWNER_DELETE_SUCCESS);
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.OPERATE_SUCCESS,ownerService.FindById(id));
    }

    @RequestMapping("/AddOwner")
    public Result AddOwner(@RequestBody Owner owner){
        if(ownerService.AddOwner(owner)){
            return new Result(true,StatusCode.OK,MessageConstant.OWNER_ADD_SUCCESS);
        }else{
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
        }
    }

    @RequestMapping("/UpdateOwner")
    public Result UpdateOwner(@RequestBody Owner owner){
        if(ownerService.UpdateOwner(owner)){
            return new Result(true,StatusCode.OK,MessageConstant.OWNER_UPDATE_SUCCESS);
        }else{
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
        }
    }

    @RequestMapping("GetOwners")
    public List<Owner> GetOwners(){
        return ownerService.GetOwners();
    }
}
