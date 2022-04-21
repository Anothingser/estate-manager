package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ChargeDetailMapper;
import com.example.estatemanager.dao.ChargeItemMapper;
import com.example.estatemanager.dao.OwnerMapper;
import com.example.estatemanager.domain.ChargeDetail;
import com.example.estatemanager.domain.ChargeItem;
import com.example.estatemanager.domain.Owner;
import com.example.estatemanager.service.ChargeDetailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.security.auth.callback.CallbackHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChargeDetailServiceImpl implements ChargeDetailService {
    @Autowired
    private ChargeDetailMapper chargeDetailMapper;
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private ChargeItemMapper chargeItemMapper;

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

    @Override
    public boolean EndOrder(Integer id) {
//        ChargeDetail chargeDetail = (ChargeDetail) Class.forName("com.example.estatemanager.domain.ChargeDetail").newInstance();
        try {
            ChargeDetail chargeDetail = chargeDetailMapper.selectByPrimaryKey(id);
            chargeDetail.setStatus(1);
            chargeDetail.setCollection(chargeDetail.getPay());
            chargeDetail.setPayTime(new Date());
            chargeDetailMapper.updateByPrimaryKeySelective(chargeDetail);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean SendCharges(Integer id,Integer sendingFlag) {
        try{
            List<Owner> list = new ArrayList<>();
            ChargeDetail chargeDetail = new ChargeDetail();
            ChargeItem chargeItem = chargeItemMapper.selectByPrimaryKey(id);
            Owner owner = new Owner();
            //制作该条数据
            chargeDetail.setCommunityId(chargeItem.getCommunityId());
            chargeDetail.setCommunityName(chargeItem.getCommunityName());
            chargeDetail.setChargeItemId(chargeItem.getId());
            chargeDetail.setChargeItemName(chargeItem.getName());
            chargeDetail.setPay(chargeItem.getMoney());
            chargeDetail.setStatus(0);
            /**
             * 发送给该小区全体业主
             */
            if(sendingFlag==1){
                owner.setCommunityId(chargeItem.getCommunityId());
                list=ownerMapper.select(owner);
                for(Owner i : list){
                    chargeDetail.setContractor(i.getName());
                    chargeDetail.setTelephone(i.getTelephone());
                    chargeDetailMapper.insert(chargeDetail);
                }
                return true;
            }else if(sendingFlag==2){
                owner.setCommunityId(chargeItem.getCommunityId());
                owner.setType(0);
                list= ownerMapper.select(owner);
                for(Owner i : list){
                    chargeDetail.setContractor(i.getName());
                    chargeDetail.setTelephone(i.getTelephone());
                    chargeDetailMapper.insert(chargeDetail);
                }
                return true;
            }else
                return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean SendSomeCharge(Integer id, List<Integer> ids) {
        try{
            ChargeDetail chargeDetail = new ChargeDetail();
            ChargeItem chargeItem = chargeItemMapper.selectByPrimaryKey(id);
            Owner owner = new Owner();
            //制作该条数据
            chargeDetail.setCommunityId(chargeItem.getCommunityId());
            chargeDetail.setCommunityName(chargeItem.getCommunityName());
            chargeDetail.setChargeItemId(chargeItem.getId());
            chargeDetail.setChargeItemName(chargeItem.getName());
            chargeDetail.setPay(chargeItem.getMoney());
            chargeDetail.setStatus(0);
            /**
             * 发送给业主
             */
            for(Integer i : ids){
                owner = ownerMapper.selectByPrimaryKey(i);
                chargeDetail.setTelephone(owner.getTelephone());
                chargeDetail.setContractor(owner.getName());
                chargeDetailMapper.insert(chargeDetail);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
