package com.eazybytes.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class accountsController {

    @GetMapping("/sayHello")
    public String hello(){
        return "hello world";
    }
}
