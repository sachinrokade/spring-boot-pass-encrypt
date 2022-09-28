package com.encrypt.demo.controller;

import com.encrypt.demo.pojo.Emp;
import com.encrypt.demo.repo.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    EmpRepo repo;


    @GetMapping("/")
    public String add(){

        repo.save(new Emp(11,"Abc"));
        repo.save(new Emp(15,"Abc"));
        repo.save(new Emp(12,"Abc"));
        repo.save(new Emp(13,"Abc"));
        return "inserted ";
    }
}
