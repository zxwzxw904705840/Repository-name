package com.example.demo.controller;

import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.subject.Subject;


@Controller
public class indexSearch {
    @Autowired
    UserService userService;


    @GetMapping(value = {"/login","","/","logout"})
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpSession session) {

        String userid = request.getParameter("userId");
        String passwd =  request.getParameter("passwd");
        UsernamePasswordToken token = new UsernamePasswordToken(userid, passwd);
        Subject subject = SecurityUtils.getSubject();
        System.out.println("userid passwd:"+userid+"   "+passwd);
        try {
            subject.login(token);  //完成登录
            UserEntity user=(UserEntity) subject.getPrincipal();
            session.setAttribute("user", user);
            session.setAttribute("userId", userid);
            return "redirect:index";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "账号密码错误！");
            return  "login";
        }

    }



    @RequestMapping("/index")
    public  String index1(Model model, HttpServletRequest request){
        String userid=request.getSession().getAttribute("userId").toString();
        System.out.println("userid.toString="+userid);
        return "index";
    }


    @RequestMapping("/ThesisListResult")
    public  String ThesisListResult(){

        return "ThesisListResult";
    }
}
