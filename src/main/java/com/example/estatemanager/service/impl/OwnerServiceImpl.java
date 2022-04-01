package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.OwnerMapper;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.service.OwnerService;
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
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerMapper ownerMapper;


    @Override
    public Page<Owner> SearchList(Map searchMap) {
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
                criteria.orLike("name", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))) {
                criteria.orLike("communityName", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<Owner>) ownerMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public Boolean DeleteOwner(List<Integer> ids) {
        try{
            for(Integer i:ids){
                ownerMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public Owner FindById(Integer id) {
        return ownerMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean AddOwner(Owner owner) {
        if(ownerMapper.insert(owner)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateOwner(Owner owner) {
        if(ownerMapper.updateByPrimaryKeySelective(owner)==1)
            return true;
        else
            return false;
    }
}
