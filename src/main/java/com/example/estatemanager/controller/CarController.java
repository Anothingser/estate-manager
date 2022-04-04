package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.domain.Car;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.service.CarService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Car> page=carService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.CAR_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/FindById")
    public Result FindById(Integer id){
        return new Result(true,StatusCode.OK,
                MessageConstant.OPERATE_SUCCESS,carService.FindById(id));
    }

    @RequestMapping("/AddCar")
    public Result AddCar(@RequestBody Car car){
        if(carService.AddCar(car))
            return new Result(true,StatusCode.OK,MessageConstant.CAR_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/UpdateCar")
    public Result UpdateCar(@RequestBody Car car){
        if(carService.UpdateCar(car))
            return new Result(true,StatusCode.OK,MessageConstant.CAR_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @PostMapping("/DeleteCar")
    public Result DeleteCar(@RequestBody List<Integer> ids){
        Boolean flag=carService.DeleteCar(ids);
        return new Result(true,StatusCode.OK,MessageConstant.CAR_DELETE_SUCCESS);
    }

    /**
     * 停车管理----根据车牌号查询车辆
     * @param carNumber
     * @return Car
     */
    @RequestMapping("FindCarByCN")
    public Result FindCarByCN(String carNumber){
        if(carService.FindCarByCN(carNumber)!=null){
            return new Result(true,StatusCode.OK,MessageConstant.OPERATE_SUCCESS,carService.FindCarByCN(carNumber));
        }else{
            return new Result(false,StatusCode.ERROR,MessageConstant.CAR_FINDCARBYCN_ERROR);
        }
    }
}
