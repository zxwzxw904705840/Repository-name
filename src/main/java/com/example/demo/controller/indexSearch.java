package com.example.demo.controller;

import com.example.demo.Entity.ThesisEntity;
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

import java.util.ArrayList;
import java.util.List;


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


    /**
     * 跳转到搜索结果论文列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/GoToThesisList")
    public  String ThesisListResult( Model model,HttpServletRequest request){
        String userid=request.getSession().getAttribute("userId").toString();
        String type=request.getParameter("selecttype1");
        String input=request.getParameter("input1");
        System.out.println("GoToThesisList,type="+type);
        List<ThesisEntity> thesisList=new ArrayList<>();

        UserEntity user=new UserEntity();
        System.out.println(" UserEntity user=new UserEntity();");
        user.setUserName("叶元卯");
        System.out.println(" user.setUserId();"+user.getUserId());
        
        //临时假数据
        for(int i=0;i<15;i++){
            ThesisEntity thesistmp=new ThesisEntity();
            thesistmp.setAuthor1(user);
            thesistmp.setJournal("机械动力期刊");
            thesistmp.setThesisId("100"+i*2+"4-2-85"+i);
            thesistmp.setThesisTitle("动态规划");
            if(i/3==0)
                thesistmp.setThesisTitle("基于视觉的天眼机器研究");
            if(i/2==0)
                thesistmp.setThesisTitle("类脑研究");
            thesisList.add(thesistmp);
        }
        if(type.equals("0")){//按论文ID搜索查询
            System.out.println("type0,thesisList"+thesisList.get(0).getThesisId());
            model.addAttribute("thesisList",thesisList);

        }else if(type.equals("1")){//按标题搜索查询

        }else if(type.equals("2")){//作者1

        }else if(type.equals("3")){//作者2

        }else if(type.equals("4")){//作者3

        }else if(type.equals("5")){//刊名

        }
        return "ThesisListResult";

    }

    /**
     * 跳转到搜索结果软件著作列表
     * @param request
     * @return
     */
    @RequestMapping("/GoToCopyrightList")
    public  String GoToCopyrightList(HttpServletRequest request){

        return "CopyrightListResult";
    }



}
