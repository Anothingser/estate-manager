package com.example.estatemanager.service;

import com.example.estatemanager.domain.Car;
import com.example.estatemanager.domain.Owner;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CarService {
    Page<Car> SearchList(Map searchMap);

    Car FindById(Integer id);

    boolean AddCar(Car car);

    boolean UpdateCar(Car car);

    Boolean DeleteCar(List<Integer> ids);
}
