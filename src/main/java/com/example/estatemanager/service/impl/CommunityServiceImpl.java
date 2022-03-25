package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.CommunityMapper;
import com.example.estatemanager.domain.Community;
import com.example.estatemanager.service.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import java.util.Date;
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
//            if(StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
//                pageNum=Integer.parseInt((String) searchMap.get("pageNum"));
//            }
            if((Integer) searchMap.get("pageNum") !=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if((Integer) searchMap.get("pageSize") !=null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        return (Page<Community>) communityMapper.selectByExample(example);
    }

    @Override
    public boolean AddCommunity(Community community) {

        Date date=new Date();
        community.setCreateTime(date);

        if(communityMapper.insert(community)==1)
            return true;
        else
            return false;
    }

    @Override
    public Community FindById(Integer id) {
        return communityMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean UpdateCommunity(Community community) {
        Date date=new Date();
        community.setUpdateTime(date);
        if(communityMapper.updateByPrimaryKey(community)==1)
            return true;
        else
            return false;
    }

    @Override
    public Boolean UpdateStatus(Integer status, Integer id) {
        Community community=new Community();
        community.setStatus(status.toString());
        community.setId(id);
        Date date=new Date();
        community.setUpdateTime(date);
        if(communityMapper.updateByPrimaryKeySelective(community)==1)
            return true;
        else
            return false;
    }

    @Override
    public Boolean DeleteCommunity(List<Integer> ids) {
        /**
         * 可优化部分，这部分应该开启事务，来保证数据的一致性
         * 【★】在删除之前，应该确认小区已停用/小区所有数据清空
         */
        for(Integer i:ids){
            communityMapper.deleteByPrimaryKey(i);
        }
        return true;
    }
}
