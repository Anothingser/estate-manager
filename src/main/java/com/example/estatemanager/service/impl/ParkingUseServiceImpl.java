package com.example.estatemanager.service.impl;

import com.example.estatemanager.dao.ParkingMapper;
import com.example.estatemanager.dao.ParkingUseMapper;
import com.example.estatemanager.domain.Parking;
import com.example.estatemanager.domain.ParkingUse;
import com.example.estatemanager.service.ParkingUseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ParkingUseServiceImpl implements ParkingUseService {
    @Autowired
    private ParkingMapper parkingMapper;

    @Autowired
    private ParkingUseMapper parkingUseMapper;

    @Override
    public Page<ParkingUse> SearchList(Map searchMap) {
        int pageNum = 1;
        int pageSize = 5;
        Example example = new Example(ParkingUse.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("communityName"))) {
                criteria.andLike("communityName", (String) "%" + (String) searchMap.get("communityName") + "%");
            }
            if (searchMap.get("status") !=null && searchMap.get("status") != ""){
                criteria.andEqualTo("status",Integer.parseInt(searchMap.get("status").toString()));
            }
            if ((Integer) searchMap.get("pageNum") != null) {
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") != null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        Page<ParkingUse> page=(Page<ParkingUse>) parkingUseMapper.selectByExample(example);
        //对totalfee的校验
        for(ParkingUse pu:page){
            if(pu.getEndTime()==null && pu.getUseType()==0){
                long sses=new Date().getTime()-pu.getStartTime().getTime();
                /**
                 * 1 小时以下免费，一小时以上2元/小时，一天10元封顶
                 */
                int fee;//费用
                if(sses<1000*60*60){
                    fee=0;
                }else{
                    fee=(int)(sses/(1000*60*60*24))*1000;//先按天计算
                    if(fee>=1000)//判断如果大于10元，则已过了一天，不适用一小时免费规则
                    {
                        fee+=((sses%(1000*60*60*24))/(1000*60*60))*200<1000?(int)((sses%(1000*60*60*24))/(1000*60*60))*200:1000;
                        //如果计费>10,则+10
                    }
                }
                System.out.println(fee);
                //fee=((sses%(1000*60*60*24))/(1000*60*60))>=1?fee+(int)((sses%(1000*60*60*24))/(1000*60*60))*200:fee;
                pu.setTotalFee(fee);
            }
        }
        return page;
    }

    @Override
    @Transactional
    public boolean AddParkingUse(ParkingUse parkingUse) {
        try{
            Parking parking=new Parking();
            parking.setCode(parkingUse.getCode());
            Parking pk=parkingMapper.selectOne(parking);
            pk.setStatus(1);
            parkingMapper.updateByPrimaryKeySelective(pk);
            parkingUseMapper.insert(parkingUse);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean UpdateParkingUse(ParkingUse parkingUse) {
        return parkingUseMapper.updateByPrimaryKeySelective(parkingUse)==1?true:false;
    }

    @Override
    @Transactional
    public boolean DeleteParkingUse(List<Integer> ids) {
        try{
            for(Integer i:ids){
                //此处应写校验
                parkingUseMapper.deleteByPrimaryKey(i);
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean FinishiParking(Integer id) {
        //Class parkingUse=ParkingUse.class;
        try{
            ParkingUse parkingUse=parkingUseMapper.selectByPrimaryKey(id);
            parkingUse.setEndTime(new Date());
            if(parkingUse.getUseType()==0){
                long sses=new Date().getTime()-parkingUse.getStartTime().getTime();
                /**
                 * 1 小时以下免费，一小时以上2元/小时，一天10元封顶
                 */
                int fee;//费用
                if(sses<1000*60*60){
                    fee=0;
                }else{
                    fee=(int)(sses/(1000*60*60*24))*1000;//先按天计算
                    if(fee>=1000)//判断如果大于10元，则已过了一天，不适用一小时免费规则
                    {
                        fee+=((sses%(1000*60*60*24))/(1000*60*60))*200<1000?(int)((sses%(1000*60*60*24))/(1000*60*60))*200:1000;
                        //如果计费>10,则+10
                    }
                }
                System.out.println(fee);
                //fee=((sses%(1000*60*60*24))/(1000*60*60))>=1?fee+(int)((sses%(1000*60*60*24))/(1000*60*60))*200:fee;
                parkingUse.setTotalFee(fee);
            }

            Parking parking=new Parking();
            parking.setCode(parkingUse.getCode());
            Parking pk=parkingMapper.selectOne(parking);
            pk.setStatus(0);
            parkingMapper.updateByPrimaryKeySelective(pk);
            parkingUseMapper.updateByPrimaryKeySelective(parkingUse);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
