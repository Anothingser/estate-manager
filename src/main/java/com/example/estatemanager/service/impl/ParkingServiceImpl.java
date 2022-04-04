package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ParkingMapper;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.domain.Parking;
import com.example.estatemanager.service.ParkingService;
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
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingMapper parkingMapper;

    @Override
    public Page<Parking> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Parking.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("communityName"))) {
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("communityName") + "%");
            }
            if (searchMap.get("status") !=null && searchMap.get("status") != ""){
                criteria.andEqualTo("status",Integer.parseInt(searchMap.get("status").toString()));
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<Parking>) parkingMapper.selectByExample(example);
    }

    @Override
    public boolean AddParking(Parking parking) {
        parking.setStatus(0);
        if(parkingMapper.insert(parking)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateParking(Parking parking) {
        return parkingMapper.updateByPrimaryKeySelective(parking)==1?true:false;
    }

    @Override
    @Transactional
    public boolean DeleteParking(List<Integer> ids) {
        try{
            for(Integer i:ids){
                parkingMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean SetStatus(Integer status, Integer id) {
        Parking parking=new Parking();
        parking.setId(id);
        parking.setStatus(status);
        return parkingMapper.updateByPrimaryKeySelective(parking)==1?true:false;
    }

    @Override
    public Parking FindById(Integer id) {
        return parkingMapper.selectByPrimaryKey(id);
    }

    @Override
    public Parking FindParkingByCode(String code) {
        Parking parking=new Parking();
        parking.setCode(code);
        return parkingMapper.selectOne(parking);
    }
}
