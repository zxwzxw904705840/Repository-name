package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexSearch {
    @GetMapping("/login")
    public  String login(){

        return "login";
    }
    @RequestMapping("/index")
    public  String index1(){

        return "index";
    }
}
