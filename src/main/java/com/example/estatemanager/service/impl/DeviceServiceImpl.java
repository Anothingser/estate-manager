package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.DeviceMapper;
import com.example.estatemanager.domain.Device;
import com.example.estatemanager.service.DeviceService;
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
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public Page<Device> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(Device.class);
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
        return (Page<Device>) deviceMapper.selectByExample(example);
    }

    @Override
    public boolean AddDevice(Device device) {
        device.setStatus(0);
        if(deviceMapper.insert(device)==1)
            return true;
        else
            return false;
    }

    @Override
    public boolean UpdateDevice(Device device) {
        if(deviceMapper.updateByPrimaryKeySelective(device)==1)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean DeleteDevice(List<Integer> ids) {
        try{
            for(Integer id : ids){
                deviceMapper.deleteByPrimaryKey(id);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Device FindById(Integer id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean BacktoNormal(Integer id) {
        Device device = new Device();
        device.setId(id);
        device.setStatus(0);
        return deviceMapper.updateByPrimaryKeySelective(device)==1?true:false;
    }
}
