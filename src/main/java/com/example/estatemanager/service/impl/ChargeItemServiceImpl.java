package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ChargeItemMapper;
import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.service.ChargeItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Service
public class ChargeItemServiceImpl implements ChargeItemService {
    @Autowired
    private ChargeItemMapper chargeItemMapper;

    @Override
    public Page<ChargeItem> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(ChargeItem.class);
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
                criteria.orLike("code", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("communityName"))){
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("communityName") + "%");
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return (Page<ChargeItem>) chargeItemMapper.selectByExample(example);
    }

    @Override
    public boolean AddChargeItem(ChargeItem chargeItem) {
        if(chargeItemMapper.insert(chargeItem)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateChargeItem(ChargeItem chargeItem) {
        if(chargeItemMapper.updateByPrimaryKeySelective(chargeItem)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean DeleteChargeItem(List<Integer> ids) {
        try{
            for(Integer i:ids){
                chargeItemMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ChargeItem FindById(Integer id) {
        return chargeItemMapper.selectByPrimaryKey(id);
    }
}
