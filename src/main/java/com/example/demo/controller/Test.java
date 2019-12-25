package com.example.demo.controller;

import com.example.demo.utils.Const;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
    @RequestMapping("/")

    public @ResponseBody String index(){

        return "/register";
    }

    @RequestMapping("/login")

    public  String login(){

        return "login";
    }
}
