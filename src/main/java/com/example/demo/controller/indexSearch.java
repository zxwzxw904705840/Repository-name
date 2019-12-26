package com.example.demo.controller;

import com.example.demo.Entity.BookEntity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        if(type.equals("0")){//按论文ID搜索查询
            thesisList.add(userService.findByThesisId(input));
            model.addAttribute("thesisList",thesisList);
        }else if(type.equals("1")){//按标题搜索查询
            thesisList=userService.findAllThesisByThesisTitleLike(input);
            model.addAttribute("thesisList",thesisList);

        }else if(type.equals("2")){//作者1
            thesisList=userService.findAllThesisByAuthor1(input);
            if(thesisList!=null&&thesisList.size()>0){
                System.out.println("按作者1："+thesisList.get(0).getThesisTitle());
            }

            model.addAttribute("thesisList",thesisList);
        }else if(type.equals("3")){//作者2
            System.out.println("input："+input);
            thesisList=userService.findAllThesisByAuthor2(input);
            if(thesisList!=null&&thesisList.size()>0){
                System.out.println("按作者2："+thesisList.get(0).getThesisTitle());
            }

            model.addAttribute("thesisList",thesisList);
        }else if(type.equals("4")){//作者3
            thesisList=userService.findAllThesisByAuthor3(input);
            model.addAttribute("thesisList",thesisList);
        }else if(type.equals("5")){//刊名
            thesisList=userService.findAllThesisByJournal(input);
            model.addAttribute("thesisList",thesisList);
        }
        return "ThesisListResult";

    }

    /**
     *  跳转到搜索结果软件著作列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/GoToCopyrightList")
    public  String GoToCopyrightList(HttpServletRequest request,Model model){
        String userid=request.getSession().getAttribute("userId").toString();
        String type=request.getParameter("selecttype2");
        String input=request.getParameter("input2");
        System.out.println("GoToCopyrightList,type="+type);
        List<BookEntity> copyrightList=new ArrayList<>();

        if(type.equals("0")){//按软件著作ID搜索查询
            copyrightList.add(userService.findByBookId(input));
            model.addAttribute("copyrightList",copyrightList);
        }else if(type.equals("1")){//按标题搜索查询
            copyrightList=userService.findByBookNameLike(input);
            model.addAttribute("copyrightList",copyrightList);
        }else if(type.equals("2")){//作者1
            copyrightList=userService.findAllBookByAuthor1(input);
            model.addAttribute("copyrightList",copyrightList);
        }else if(type.equals("3")){//作者2
            copyrightList=userService.findAllBookByAuthor2(input);
            model.addAttribute("copyrightList",copyrightList);
        }else if(type.equals("4")){//作者3
            copyrightList=userService.findAllBookByAuthor3(input);
            model.addAttribute("copyrightList",copyrightList);
        }
        return "CopyrightListResult";
    }


    /**
     * 跳转到论文ID对应的论文详情界面
     * @param request
     * @param ThesisId
     * @param model
     * @return
     */
    @RequestMapping("/ThesisDetail/{ThesisId}")
    public  String ThesisDetail(HttpServletRequest request, @PathVariable("ThesisId") String ThesisId, Model model){
        System.out.println("/ThesisDetail/{ThesisId}+"+ThesisId);
        String thesisId=ThesisId.replace("--2F-2F-", "/");
        //临时假数据
        ThesisEntity thesistmp=new ThesisEntity();
        thesistmp=userService.findByThesisId(thesisId);
        System.out.println("thsistmp:"+thesistmp.getThesisId());

        if(thesistmp.getUrl()==null){
            thesistmp.setUrl("");
        }
        model.addAttribute("thesisinf",thesistmp);

        return "ThesisDetail";
    }

    @RequestMapping("/CopyrightDetail/{CopyrightId}")
    public  String CopyrightDetail(HttpServletRequest request, @PathVariable("CopyrightId") String CopyrightId, Model model){
        System.out.println("/CopyrightDetail/{CopyrightId}+"+CopyrightId);
        //临时假数据
        BookEntity tmp=new BookEntity();
        UserEntity user=new UserEntity();
        UserEntity user2=new UserEntity();
        UserEntity user3=new UserEntity();
        String string = "2016-10-24";
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(date);
        user.setUserName("叶元卯");
        user2.setUserName("刘新");
        user3.setUserName("袁学海");
        tmp.setBookId(CopyrightId);
        tmp.setAuthor1(user);
        tmp.setBookName("博思高视频车位引导系统软件V2.1.7.0");
        tmp.setBookInformation("用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^用行车记录仪以及车尾视像头引导停车^_^");
        tmp.setBookPublishDate(date);
        tmp.setAuthor2(user2);
        tmp.setAuthor3(user3);
        tmp.setBookPublishStatus(Const.BookPublishStatus.PUBLISHED);
        tmp.setCreativeNature(Const.BookCreativeNature.ORIGINAL);
        model.addAttribute("copyrightinf",tmp);
        return "CopyrightDetail";
    }

}
