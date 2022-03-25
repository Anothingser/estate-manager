package com.example.estatemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.estatemanager.dao")
@EnableTransactionManagement
public class EstateManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateManagerApplication.class, args);
    }

}
