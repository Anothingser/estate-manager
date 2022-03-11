package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.CommunityMapper;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.service.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<Community> communityTable() {
        return communityMapper.selectAll();
    }

    @Override
    public Page<Community> SearchList(Map searchMap) {
        int pageNum=1;
        int pageSize=5;
        Example example=new Example(Community.class);
        Example.Criteria criteria=example.createCriteria();
        if(searchMap!=null){
            //时间判断
            if(StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("createTime",searchMap.get("startTime"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andGreaterThanOrEqualTo("createTime",searchMap.get("endTime"));
            }
            //名称模糊查询
            if(StringUtil.isNotEmpty((String) searchMap.get("name"))){
                criteria.andLike("name",(String) "%"+(String) searchMap.get("name")+"%");
            }
            //分页内容
            if(StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
                pageNum=Integer.parseInt((String) searchMap.get("pageNum"));
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        return (Page<Community>) communityMapper.selectByExample(example);
    }
}
