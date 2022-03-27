package com.example.estatemanager.controller;

import com.example.estatemanager.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/")
public class HouseController {

    @Autowired
    private HouseService houseService;


}
