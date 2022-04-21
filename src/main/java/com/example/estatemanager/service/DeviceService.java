package com.example.estatemanager.service;

import com.example.estatemanager.domain.Device;
import com.example.estatemanager.domain.Parking;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    Page<Device> SearchList(Map searchMap);

    boolean AddDevice(Device device);

    boolean UpdateDevice(Device device);

    boolean DeleteDevice(List<Integer> ids);

    Device FindById(Integer id);

    boolean BacktoNormal(Integer id);
}
