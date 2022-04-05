package com.example.estatemanager.service;

import com.example.estatemanager.domain.Complaint;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ComplaintService {
    Page<Complaint> SearchList(Map searchMap);

    boolean AddComplaint(Complaint complaint);

    boolean UpdateComplaint(Complaint complaint);

    boolean DeleteComplaint(List<Integer> ids);

    boolean NextStep(Integer id, Integer status);
}
