package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.BuildingMapper;
import com.example.estatemanager.dao.CommunityMapper;
import com.example.estatemanager.domain.Building;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.service.BuildingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    BuildingMapper buildingMapper;
    @Autowired
    CommunityMapper communityMapper;

    @Override
    public List<Building> GetBuildingList() {
        return buildingMapper.selectAll();
    }

    @Override
    @Transactional
    public int AddBuilding(Building building) {
        try{
            buildingMapper.insert(building);
            Community community=communityMapper.selectByPrimaryKey(building.getCommunityId());
            community.setBuildingNum(community.getBuildingNum()+1);//小区栋数+1
            Date date=new Date();
            building.setCreateTime(date);
            community.setUpdateTime(date);
            communityMapper.updateByPrimaryKey(community);
            buildingMapper.insert(building);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    @Transactional
    public Boolean DeleteBuilding(List<Integer> ids) {
        try{
            for(Integer id : ids){
                Building building=buildingMapper.selectByPrimaryKey(id);
                Community community=communityMapper.selectByPrimaryKey(building.getCommunityId());
                community.setBuildingNum(community.getBuildingNum()-1);
                Date date=new Date();
                community.setUpdateTime(date);
                communityMapper.updateByPrimaryKey(community);
                buildingMapper.deleteByPrimaryKey(id);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Building> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Building.class);
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
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("name") + "%");
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
        return (Page<Building>) buildingMapper.selectByExample(example);
    }

    @Override
    public boolean UpdateBuilding(Building building) {
        Date date=new Date();
        try{
            building.setUpdateTime(date);
            buildingMapper.updateByPrimaryKeySelective(building);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
