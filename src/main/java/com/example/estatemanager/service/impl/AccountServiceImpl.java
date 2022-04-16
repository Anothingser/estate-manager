package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.*;
import com.example.estatemanager.domain.*;
import com.example.estatemanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private ParkingMapper parkingMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private ChargeDetailMapper chargeDetailMapper;
    @Autowired
    private ComplaintMapper complaintMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    @Transactional
    public boolean regist(Account account) {
        Owner owner=new Owner();
        owner.setTelephone(account.getUsername());
        owner.setType(account.getType());
        //Owner own=ownerMapper.selectOne(owner);//查找存不存在对应人
        if(ownerMapper.selectCount(owner)>0){
            Account acc=new Account();
            acc.setUsername(account.getUsername());
            if(accountMapper.selectCount(acc)>0){
                return false;
            }else{
                account.setOwnerId(ownerMapper.selectOne(owner).getId());
                accountMapper.insert(account);
                return true;
            }
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public Account login(Account account) {
        Example example=new Example(Account.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("username", account.getUsername());
        criteria.andEqualTo("password", account.getPassword());
        try{
            Account deaccount=accountMapper.selectOneByExample(example);
            Account reaccount=new Account();
            reaccount.setUsername(deaccount.getUsername());
            reaccount.setType(deaccount.getType());
            reaccount.setOwnerId(deaccount.getOwnerId());
            return reaccount;
        }catch (Exception e){
            return new Account();
        }

    }

    /**
     * @页面 Mainpage
     * @param ownerId
     * @return 对应小区的停车进度
     */
    @Override
    public ParkingProgress Getparkingprogress(Integer ownerId) {
        Owner owner= ownerMapper.selectByPrimaryKey(ownerId);
        ParkingProgress parkingProgress=new ParkingProgress();
        parkingProgress.setCommunityName(owner.getCommunityName());
        Parking parking=new Parking();
        parking.setCommunityId(owner.getCommunityId());
        int total=parkingMapper.selectCount(parking);
        parking.setStatus(1);
        int use=parkingMapper.selectCount(parking);
        parkingProgress.setProgress(use*100/total);
        if(parkingProgress.getProgress()<40)
            parkingProgress.setStatus("success");
        else if(parkingProgress.getProgress()<60)
            parkingProgress.setStatus("");
        else if(parkingProgress.getProgress()<60)
            parkingProgress.setStatus("warning");
        else
            parkingProgress.setStatus("exception");
        return parkingProgress;
    }

    /**
     * @页面 Mainpage
     * @param ownerId
     * @return 健康状态
     */
    @Override
    public Integer Getownerhealth(Integer ownerId) {
        Owner owner=ownerMapper.selectByPrimaryKey(ownerId);
        return owner.getDanger();
    }

    /**
     * @页面 Person
     * @param ownerId
     * @return 用户的车辆
     */
    @Override
    public List<Car> getCar(Integer ownerId) {
        Car car=new Car();
        car.setOwnerId(ownerId);
        return carMapper.select(car);
    }


    /**
     * @页面 Person
     * @param houseid
     * @return 居住的房子
     */
    @Override
    public House gethouse(Integer houseid) {
        return houseMapper.selectByPrimaryKey(houseid);
    }

    /**
     * @页面 Charge
     * @param ownerId
     * @return 返回用户所有缴费
     */
    @Override
    public List<ChargeDetail> getallcharges(Integer ownerId) {
        ChargeDetail chargeDetail=new ChargeDetail();
        chargeDetail.setContractor(ownerMapper.selectByPrimaryKey(ownerId).getName());
        return chargeDetailMapper.select(chargeDetail);
    }

    /**
     * @页面 Charge
     * @param searchMap
     * @return 根据页面筛选
     */
    @Override
    public List<ChargeDetail> regetcharge(Map searchMap) {
        Example example=new Example(ChargeDetail.class);
        Example.Criteria criteria=example.createCriteria();
        if(StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
            criteria.andGreaterThanOrEqualTo("createTime",searchMap.get("startTime"));
        }
        if(StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
            criteria.andLessThanOrEqualTo("createTime",searchMap.get("endTime"));
        }
        return chargeDetailMapper.selectByExample(example);
    }

    @Override
    public boolean pay(Integer id) {
        ChargeDetail chargeDetail=new ChargeDetail();
        chargeDetail.setStatus(1);
        chargeDetail.setId(id);
        if(chargeDetailMapper.updateByPrimaryKeySelective(chargeDetail)==1)
            return true;
        else
            return false;
    }

    @Override
    public List<Parking> getparkings(Map condition) {
        //设置筛选条件
        Example example=new Example(Parking.class);
        Example.Criteria criteria = example.createCriteria();
        String cname=ownerMapper.selectByPrimaryKey(condition.get("ownerId")).getCommunityName();
        if(StringUtil.isNotEmpty((String) condition.get("item"))){
            criteria.andCondition("(name like \"%"+condition.get("item")+"%\" or code like \"%" + condition.get("item")+"%\")");
        }
        criteria.andCondition("community_name = \"" + cname+"\"");
        return parkingMapper.selectByExample(example);
    }

    @Override
    public List<Complaint> getcomplaint(Integer ownerId) {
        Complaint complaint=new Complaint();
        complaint.setOwnerId(ownerId);
        return complaintMapper.select(complaint);
    }

    @Override
    public List<Device> getdevices() {
        return deviceMapper.selectAll();
    }

    @Override
    public List<Repair> getrepairs(Integer ownerId) {
        Repair repair=new Repair();
        repair.setOwnerId(ownerId);
        return repairMapper.select(repair);
    }

    @Override
    public List<Building> getbuildings(Integer ownerId) {
        Building building=new Building();
        building.setCommunityId(ownerMapper.selectByPrimaryKey(ownerId).getCommunityId());
        return buildingMapper.select(building);
    }


}
