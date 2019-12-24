package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SoftwareCopyrightManagement {
    @RequestMapping("/MySoftwareCopyright")
    public  String MySoftwareCopyright(){

        return "MySoftwareCopyright";
    }
}
