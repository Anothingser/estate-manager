package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.CarMapper;
import com.example.estatemanager.domain.Car;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.service.CarService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public Page<Car> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Owner.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            //时间判断
            if (StringUtil.isNotEmpty((String) searchMap.get("startTime"))) {
                criteria.andGreaterThanOrEqualTo("createTime", searchMap.get("startTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("endTime"))) {
                criteria.andGreaterThanOrEqualTo("createTime", searchMap.get("endTime"));
            }
            //名称模糊查询
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))) {
                criteria.orEqualTo("communityId", (String) "%" + (String) searchMap.get("communityId") + "%");
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))) {
                criteria.orLike("ownerName", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<Car>) carMapper.selectByExample(example);
    }

    @Override
    public Car FindById(Integer id) {
        return carMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean AddCar(Car car) {
        if(carMapper.insert(car)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateCar(Car car) {
        if(carMapper.updateByPrimaryKeySelective(car)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public Boolean DeleteCar(List<Integer> ids) {
        try{
            for(Integer i:ids){
                carMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }



}
