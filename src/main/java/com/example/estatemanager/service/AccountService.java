package com.example.estatemanager.service;

import com.example.estatemanager.domain.*;

import java.util.List;
import java.util.Map;

public interface AccountService {
    boolean regist(Account account);

    Account login(Account account);

    ParkingProgress Getparkingprogress(Integer ownerId);

    Integer Getownerhealth(Integer ownerId);

    List<Car> getCar(Integer ownerId);

    House gethouse(Integer houseid);

    List<ChargeDetail> getallcharges(Integer ownerId);

    List<ChargeDetail> regetcharge(Map searchMap);

    boolean pay(Integer id);

    List<Parking> getparkings(Map condition);

    List<Complaint> getcomplaint(Integer ownerId);

    List<Device> getdevices();

    List<Repair> getrepairs(Integer ownerId);

    List<Building> getbuildings(Integer ownerId);

    boolean changeCarType(Car car);
}
