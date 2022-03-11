package com.example.estatemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.estatemanager.dao")
public class EstateManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateManagerApplication.class, args);
    }

}
