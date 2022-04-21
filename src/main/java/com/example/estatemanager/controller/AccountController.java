package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.*;
import com.example.estatemanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    public Result Login(@RequestBody Account account){
        if(accountService.login(account).equals(new Account())){
            return new Result(false,StatusCode.ERROR,MessageConstant.ACCOUNT_LOGIN_FAILED);
        }else{
            return new Result(true,StatusCode.OK, MessageConstant.ACCOUNT_LOGIN_SUCCESS,accountService.login(account));
        }
    }

    @RequestMapping("/regist")
    public Result Regist(@RequestBody Account account){
        if(accountService.regist(account)){
            return new Result(true,StatusCode.OK, MessageConstant.ACCOUNT_REGIST_SUCCESS);
        }else{
            return new Result(false,StatusCode.ERROR,MessageConstant.ACCOUNT_REGIST_FAILED);
        }
    }

    /**
     * 主页面停车进度
     * @param ownerId
     * @return
     */
    @RequestMapping("getparkingprogress")
    public ParkingProgress Getparkingprogress(Integer ownerId){
        return accountService.Getparkingprogress(ownerId);
    }

    /**
     * 主页面防疫状态
     */
    @RequestMapping("getownerhealth")
    public Integer Getownerhealth(Integer ownerId){
        return accountService.Getownerhealth(ownerId);
    }

    /**
     * 返回用户的车列表
     */
    @RequestMapping("getusecars")
    public List<Car> getCar(Integer ownerId){
        return accountService.getCar(ownerId);
    }

    /**
     * 返回用户的房子
     */
    @RequestMapping("getusehouse")
    public House gethouse(Integer houseid){
        return accountService.gethouse(houseid);
    }


    /**
     * 返回用户的所有单据
     */
    @RequestMapping("getallcharges")
    public List<ChargeDetail> getallcharges(Integer ownerId){
        return accountService.getallcharges(ownerId);
    }

    @RequestMapping("regetcharge")
    public List<ChargeDetail> regetcharge(@RequestBody Map searchMap){
        return accountService.regetcharge(searchMap);
    }

    @RequestMapping("pay")
    public Result pay(Integer id){
        if(accountService.pay(id))
            return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }


    /**
     *找到业主对应的小区的所有停车位
     * @param condition
     * @return
     */
    @RequestMapping("getparkings")
    public List<Parking> getparkings(@RequestBody Map condition){
        return accountService.getparkings(condition);
    }


    /**
     * 找到业主对应的抱怨
     */
    @RequestMapping("getcomplaint")
    public List<Complaint> getcomplaint(Integer ownerId){
        return accountService.getcomplaint(ownerId);
    }

    /**
     * 获取所有的设备列表
     */
    @RequestMapping("getdevices")
    public List<Device> getdevices(){
        return accountService.getdevices();
    }

    /**
     * 获取该业主对应报修信息
     */
    @RequestMapping("getrepairs")
    public List<Repair> getrepairs(Integer ownerId){
        return accountService.getrepairs(ownerId);
    }

    /**
     * 获取业主对应楼栋
     */
    @RequestMapping("getbuildings")
    public List<Building> getbuildings(Integer ownerId){
        return accountService.getbuildings(ownerId);
    }

    /**
     * 改变停车类型
     */
    @RequestMapping("changeCarType")
    public Result changeCarType(@RequestBody Car car){
        if(accountService.changeCarType(car))
            return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

}
