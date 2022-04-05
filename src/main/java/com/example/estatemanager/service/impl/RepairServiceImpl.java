package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.RepairMapper;
import com.example.estatemanager.domain.Repair;
import com.example.estatemanager.service.RepairService;
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
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public Page<Repair> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Repair.class);
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
                criteria.andLike("ownerName", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("communityName"))){
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("communityName") + "%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("buildingName"))){
                criteria.andLike("buildingName", (String) "%" + (String) searchMap.get("buildingName") + "%");
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<Repair>) repairMapper.selectByExample(example);
    }

    @Override
    public boolean AddRepair(Repair repair) {
        if(repairMapper.insert(repair)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateRepair(Repair repair) {
        if(repairMapper.updateByPrimaryKeySelective(repair)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean DeleteRepair(List<Integer> ids) {
        try{
            for(Integer i:ids){
                repairMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean NextStep(Integer id, Integer status) {
        Repair repair=new Repair();
        repair.setId(id);
        repair.setStatus(status);
        if(repairMapper.updateByPrimaryKeySelective(repair)==1)
            return true;
        else
            return false;
    }
}
