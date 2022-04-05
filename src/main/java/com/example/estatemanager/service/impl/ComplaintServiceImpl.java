package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ComplaintMapper;
import com.example.estatemanager.domain.Complaint;
import com.example.estatemanager.domain.Repair;
import com.example.estatemanager.service.ComplaintService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public Page<Complaint> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Complaint.class);
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
        return (Page<Complaint>) complaintMapper.selectByExample(example);
    }

    @Override
    public boolean AddComplaint(Complaint complaint) {
        complaint.setStatus(0);
        if(complaintMapper.insert(complaint)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateComplaint(Complaint complaint) {
        if(complaintMapper.updateByPrimaryKeySelective(complaint)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean DeleteComplaint(List<Integer> ids) {
        try{
            for(Integer i:ids){
                complaintMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean NextStep(Integer id, Integer status) {
        Complaint complaint=new Complaint();
        complaint.setId(id);
        complaint.setStatus(status);
        if(complaintMapper.updateByPrimaryKeySelective(complaint)==1)
            return true;
        else
            return false;
    }
}
