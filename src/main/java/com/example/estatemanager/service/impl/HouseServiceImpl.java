package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.BuildingMapper;
import com.example.estatemanager.dao.HouseMapper;
import com.example.estatemanager.domain.Building;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.domain.House;
import com.example.estatemanager.service.HouseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public Page<House> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(House.class);
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
                criteria.andLike("name", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("communityName"))){
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("communityName") + "%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("buildingName"))){
                criteria.andLike("buildingName", (String) "%" + (String) searchMap.get("buildingName") + "%");
            }
            //分页内容
//            if(StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
//                pageNum=Integer.parseInt((String) searchMap.get("pageNum"));
//            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<House>) houseMapper.selectByExample(example);
    }

    @Override
    public House FindById(Integer id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int AddHouse(House house) {
        try{
            Building building=buildingMapper.selectByPrimaryKey(house.getBuildingId());
            building.setHouseholdsNum(building.getHouseholdsNum()+1);//楼房数目
            // +1
            Date date=new Date();
            house.setCreateTime(date);
            building.setUpdateTime(date);
            buildingMapper.updateByPrimaryKey(building);
            houseMapper.insert(house);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public boolean UpdateHouse(House house) {
        Date date=new Date();
        try{
            house.setUpdateTime(date);
            houseMapper.updateByPrimaryKeySelective(house);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean DeleteHouse(List<Integer> ids) {
        try{
            for(Integer id : ids){
                House house=houseMapper.selectByPrimaryKey(id);
                Building building=buildingMapper.selectByPrimaryKey(house.getCommunityId());
                building.setHouseholdsNum(building.getHouseholdsNum()-1);
                Date date=new Date();
                building.setUpdateTime(date);
                buildingMapper.updateByPrimaryKey(building);
                houseMapper.deleteByPrimaryKey(id);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
