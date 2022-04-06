package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ChargeDetailMapper;
import com.example.estatemanager.domain.ChargeDetail;
import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.service.ChargeDetailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.security.auth.callback.CallbackHandler;
import java.util.List;
import java.util.Map;

@Service
public class ChargeDetailServiceImpl implements ChargeDetailService {
    @Autowired
    private ChargeDetailMapper chargeDetailMapper;

    @Override
    public Page<ChargeDetail> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(ChargeDetail.class);
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
                criteria.orLike("chargeItemName", (String) "%" + (String) searchMap.get("name") + "%");
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))) {
                criteria.orLike("contractor", (String) "%" + (String) searchMap.get("name") + "%");
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
        return (Page<ChargeDetail>) chargeDetailMapper.selectByExample(example);
    }

    @Override
    public boolean AddChargeDetail(ChargeDetail chargeDetail) {
        if(chargeDetail.getCollection()!=null)
            chargeDetail.setStatus(1);
        else
            chargeDetail.setStatus(0);
        if(chargeDetailMapper.insert(chargeDetail)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateChargeDetail(ChargeDetail chargeDetail) {
        if(chargeDetail.getCollection()!=null)
            chargeDetail.setStatus(1);
        if(chargeDetailMapper.updateByPrimaryKeySelective(chargeDetail)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean DeleteChargeDetail(List<Integer> ids) {
        try{
            for(Integer i :ids){
                chargeDetailMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ChargeDetail FindById(Integer id) {
        return chargeDetailMapper.selectByPrimaryKey(id);
    }

}
