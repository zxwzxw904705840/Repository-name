package com.example.demo.controller;

import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.security.auth.Subject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class indexSearch {


    @GetMapping(value = {"/login","","/"})
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(Model model,@Valid UserEntity userVo, HttpSession session) {

        String userid = userVo.getUserId();
        String passwd = userVo.getPassword();
System.out.println("userEntity:"+userVo.getUserId()+"   "+passwd);
        try {
            Result res;
           // System.out.println("res.message:"+res.getMessage());
         //   System.out.println("res.object:"+res.getObject(passwd));
  //          if(res.getObject(passwd)==passwd)
                return  "redirect:/index";
//            else
//                return "login";
        } catch (Exception e) {
            model.addAttribute("error", "账号密码错误！");
            return  "login";

        }



    }



    @RequestMapping("/index")
    public  String index1(){

        return "index";
    }


    @RequestMapping("/ThesisListResult")
    public  String ThesisListResult(){

        return "ThesisListResult";
    }
}
