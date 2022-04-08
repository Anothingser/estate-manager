package com.example.estatemanager.controller;

import com.example.estatemanager.common.MessageConstant;
import com.example.estatemanager.common.PageResult;
import com.example.estatemanager.common.Result;
import com.example.estatemanager.common.StatusCode;
import com.example.estatemanager.dao.ComplaintMapper;
import com.example.estatemanager.domain.Complaint;
import com.example.estatemanager.domain.Repair;
import com.example.estatemanager.service.ComplaintService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @RequestMapping("/SearchList")
    public PageResult SearchList(@RequestBody Map searchMap){
        Page<Complaint> page=complaintService.SearchList(searchMap);
        return new PageResult(true, StatusCode.OK,
                MessageConstant.COMPLAINT_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("AddComplaint")
    public Result AddComplaint(@RequestBody Complaint complaint){
        if(complaintService.AddComplaint(complaint))
            return new Result(true,StatusCode.OK,MessageConstant.COMPLAINT_ADD_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("UpdateComplaint")
    public Result UpdateComplaint(@RequestBody Complaint complaint){
        if(complaintService.UpdateComplaint(complaint))
            return new Result(true,StatusCode.OK,MessageConstant.COMPLAINT_UPDATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("DeleteComplaint")
    public Result DeleteComplaint(@RequestBody List<Integer> ids){
        if(complaintService.DeleteComplaint(ids))
            return new Result(true,StatusCode.OK,MessageConstant.COMPLAINT_DELETE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    @RequestMapping("/NextStep")
    public Result NextStep(Integer id, Integer status){
        if(complaintService.NextStep(id,status))
            return new Result(true, StatusCode.OK, MessageConstant.OPERATE_SUCCESS);
        else
            return new Result(false,StatusCode.ERROR,MessageConstant.SYSTEM_BUSY);
    }

    /**
     * 导航页——最新的抱怨条数
     * @return Count
     */
    @RequestMapping("/complaintCount")
    public Integer complaintCount(){
        return complaintService.complaintCount();
    }

    @RequestMapping("GetTen")
    public List<Complaint> GetTen(){
        return complaintService.GetTen();
    }
}
