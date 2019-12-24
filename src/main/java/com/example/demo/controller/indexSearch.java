package com.example.demo.controller;

import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        user.setUserName("叶元卯");

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

//跳转到论文ID对应的论文详情界面
    @RequestMapping("/ThesisDetail/{ThesisId}")
    public  String ThesisDetail(HttpServletRequest request, @PathVariable("ThesisId") String ThesisId, Model model){
        System.out.println("/ThesisDetail/{ThesisId}+"+ThesisId);
        //临时假数据
        ThesisEntity thesistmp=new ThesisEntity();
        UserEntity user=new UserEntity();
        UserEntity user2=new UserEntity();
        UserEntity user3=new UserEntity();
        user.setUserName("叶元卯");
        user2.setUserName("刘新");
        user3.setUserName("袁学海");
        thesistmp.setAuthor1(user);
        thesistmp.setJournal("机械动力期刊");
        thesistmp.setThesisId(ThesisId);
        thesistmp.setThesisTitle("动态规划");
        thesistmp.setAuthor2(user2);
        thesistmp.setAuthor3(user3);
        thesistmp.setPages(2);
        thesistmp.setPrivacy(Const.ThesisPrivacy.PUBLIC);
        thesistmp.setUrl("http://kns.cnki.net//KXReader/Detail?TIMESTAMP=637128076425118750&DBCODE=CJFQ&TABLEName=CJFDLAST2015&FileName=JJYD201504007&RESULT=1&SIGN=fZDoiQetL2qN%2b2B89OOattl2n8I%3d");
        thesistmp.setVolume("02期");
        System.out.println("thsistmp:"+thesistmp.getAuthor2().getUserName());
        model.addAttribute("thesisinf",thesistmp);

        return "ThesisDetail";
    }

}
