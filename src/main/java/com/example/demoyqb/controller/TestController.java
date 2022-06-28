package com.example.demoyqb.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demoyqb.properties.YqbProperties;

@RestController
public class TestController {
    @Autowired
    private YqbProperties yqbProperties;

    @GetMapping("test")
    public void test(){
        System.out.println("yqbProperties = " + JSON.toJSONString(yqbProperties));
    }

}
