package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.*;
import com.example.estatemanager.domain.*;
import com.example.estatemanager.service.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private ParkingMapper parkingMapper;

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

    @Override
    public ArrayList GetCommunities() {
        ArrayList communities= new ArrayList();
        List<Community> cms= communityMapper.selectAll();
        int i=1;
        for(Community cm :cms){
            communities.add(cm.getName());
            i++;
        }
        return communities;
    }

    /**
     * 获取总计数
     * @return TotalCount
     */
    @Override
    public TotalCount GetTotals() {
        TotalCount totalCount=new TotalCount();
        totalCount.setTotalCommunities(communityMapper.selectCount(new Community()));
        totalCount.setTotalBuildings(buildingMapper.selectCount(new Building()));
        totalCount.setTotalHouses(houseMapper.selectCount(new House()));
        Owner owner=new Owner();
        totalCount.setTotalPeople(ownerMapper.selectCount(owner));
        owner.setType(2);
        totalCount.setTotalTenant(ownerMapper.selectCount(owner));
        return totalCount;
    }

    /**
     * 主页面停车位对应方法
     * @return
     */
    @Override
    @Transactional
    public List<ParkingProgress> GetParkingProgress() {
        List<ParkingProgress> list=new ArrayList<>();;
        List<Integer> idlist = new ArrayList<>();
        List<Community> clist=communityMapper.selectAll();
        Parking parking=new Parking();
        for(Community community : clist){
            Integer i=community.getId();
            idlist.add(i);
        }
        for(Integer id : idlist){
            ParkingProgress parkingProgress=new ParkingProgress();
            parking.setCommunityId(id);
            //获得分母，对应社区id
            Integer total=parkingMapper.selectCount(parking);
            parking.setStatus(1);
            //获得分子
            Integer use=parkingMapper.selectCount(parking);
            //设置进度
            if(total==0) parkingProgress.setProgress(0);else parkingProgress.setProgress(use*100/total);
            //设置社区名字
            parkingProgress.setCommunityName(communityMapper.selectByPrimaryKey(id).getName());
            //设置status
            if(parkingProgress.getProgress()<40)
                parkingProgress.setStatus("success");
            else if(parkingProgress.getProgress()<60)
                parkingProgress.setStatus("");
            else if(parkingProgress.getProgress()<60)
                parkingProgress.setStatus("warning");
            else
                parkingProgress.setStatus("exception");
            //添加到返回列表
            list.add(parkingProgress);
        }
        return list;
    }

    @Override
    public List<String> communityNames() {
        List<String> list=new ArrayList<>();
        List<Community> clist=communityMapper.selectAll();
        for(Community community : clist){
            list.add(community.getName());
        }
        return list;
    }

    @Override
    public List<Value> communityValues() {
        List<Value> list=new ArrayList<>();
        List<Community> clist=communityMapper.selectAll();
        Owner owner=new Owner();
        for(Community community : clist){
            Value value=new Value();
            value.setName(community.getName());//设定小区名称
            owner.setCommunityName(community.getName());
            Integer people=ownerMapper.selectCount(owner);
            value.setValue(people);
            list.add(value);
            owner.setCommunityName("");
        }
        return list;
    }

    /**
     * 防疫状况
     * @return 防疫状态
     */
    @Override
    public Integer GetDanger() {
        Building building=new Building();
        building.setDanger(2);
        if(buildingMapper.selectCount(building)>0)
            return 2;
        else{
            building.setDanger(1);
            if(buildingMapper.selectCount(building)>0)
                return 1;
            else
                return 0;
        }
    }


    /**
     * 人数饼图的实体类
     */
    @Data
    public class Value
    {
        private String name;

        private Integer value;
    }
}
