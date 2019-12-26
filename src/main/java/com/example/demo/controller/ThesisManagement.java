package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ThesisManagement {

    @Autowired
    UserService userService;


    /**
     * 用户个人论文展示列表界面
     * @return
     */
    @RequestMapping("/MyThesis")
    public  String MyThesis(HttpServletRequest request, Model model){
        String userid=request.getSession().getAttribute("userId").toString();
        List<String> urlreal=new ArrayList<>();
        List<ThesisEntity> thesisList=new ArrayList<>();
        thesisList=userService.findAllThesisByAuthorId(userid);
        //先把url中的“/”都替换成“%2F”
        for(int i=0;i<thesisList.size();i++){
            String str=thesisList.get(i).getThesisId();
            urlreal.add(i,str);
            String str2=str.replace("/", "--2F-2F-");
            thesisList.get(i).setThesisId(str2);;
        }

        model.addAttribute("thesisList",thesisList);
        model.addAttribute("urlreal",urlreal);
        return "MyThesis";
    }



    //跳转到论文登记界面
    @RequestMapping("/AddThesis")
    public String AddThesis(HttpServletRequest request) {
        String userid=request.getSession().getAttribute("userId").toString();
        return "AddThesis";
    }

    /**提交新增论文信息，论文ID就是文章编号，属于必填项
     *
     * @param request
     * @return
     */
    @RequestMapping("/AddThesisSubmit")
    public String submit(HttpServletRequest request) {
        ThesisEntity thesis =new ThesisEntity();
        UserEntity user=new UserEntity();
        String userid=request.getSession().getAttribute("userId").toString();
        user=userService.getUserById(userid);

        String title=request.getParameter("input1");
        String thesisId=request.getParameter("input2");
        String author1name;
        String author2name;
        String author3name;
        String journal;
        String volume;
        String pages;
        String url;
        String privacy=request.getParameter("optionsRadiosinline");
        UserEntity user1=new UserEntity();
        UserEntity user2=new UserEntity();
        UserEntity user3=new UserEntity();

        if(request.getParameter("input3")!=null&&request.getParameter("input3")!=""){
            author1name=request.getParameter("input3");
            user1=userService.getUserByName(author1name);
            thesis.setAuthor1(user1);
        }
        if(request.getParameter("input4")!=null&&request.getParameter("input4")!=""){
            author2name=request.getParameter("input4");
            user2=userService.getUserByName(author2name);
            thesis.setAuthor2(user2);
        }
        if(request.getParameter("input5")!=null&&request.getParameter("input5")!=""){
            author3name=request.getParameter("input5");
            user3=userService.getUserByName(author3name);
            thesis.setAuthor3(user3);
        }
        if(request.getParameter("input6")!=null&&request.getParameter("input6")!=""){
            journal=request.getParameter("input6");
            thesis.setJournal(journal);
        }
        if(request.getParameter("input7")!=null&&request.getParameter("input7")!=""){
            volume=request.getParameter("input7");
            thesis.setVolume(volume);
        }
        if(request.getParameter("input8")!=null&&request.getParameter("input8")!=""){
            pages=request.getParameter("input8");
            thesis.setPages(Integer.parseInt(pages));
        }
        if(request.getParameter("input10")!=null&&request.getParameter("input10")!=""){
            url=request.getParameter("input10");
            thesis.setUrl(url);
        }

        thesis.setThesisId(thesisId);
        thesis.setThesisTitle(title);
        thesis.setPrivacy(Const.ThesisPrivacy.class.getEnumConstants()[Integer.parseInt(privacy)]);
        thesis.setStatus(Const.ThesisStatus.class.getEnumConstants()[1]);
        System.out.println("/AddThesisSubmit userid:"+userid+" title:"+title+"  constprivacy"+thesis.getPrivacy()+" status"+thesis.getStatus());
        System.out.println("result:"+userService.addThesis(thesis,user));
        return "redirect:MyThesis";
    }

    /**
     * 前往修改论文界面
     * @param request
     * @param ThesisId
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/EditThesis/{ThesisId}")
    public String GoToEditThesis(HttpServletRequest request, @PathVariable("ThesisId") String ThesisId, Model model) {

        String thesisId=ThesisId.replace("--2F-2F-", "/");
        System.out.println("/GoToEditThesis{ThesisId}"+thesisId);
        ThesisEntity thesistmp=new ThesisEntity();
        thesistmp=userService.findByThesisId(thesisId);
        if(thesistmp.getUrl()==null){
            thesistmp.setUrl("");
        }
        model.addAttribute("thesisinf",thesistmp);
        return "EditThesis";
    }

    //修改某篇论文信息
    @ResponseBody
    @RequestMapping("/editThesis")
    public String editThesis(HttpServletRequest request) {
        JSONObject result=new JSONObject();
        result.put("message","success");
        return "redirect:ThesisDetail";
    }

    //批量删除论文
    @ResponseBody
    @RequestMapping("/deleteThesis")
    public String deleteThesis(HttpServletRequest request,@RequestParam("params")List<String> thsisidlist) {
        String userid=request.getSession().getAttribute("userId").toString();
        UserEntity user=new UserEntity();
        user=userService.getUserById(userid);
        System.out.println("/deleteThesis:"+thsisidlist);
        for(int i=0;i<thsisidlist.size();i++){
            ThesisEntity thesis=new ThesisEntity();
            thesis=userService.findByThesisId(thsisidlist.get(i));
            System.out.println("delete "+i+"thesis"+thesis.getThesisId());
            userService.deleteThesis(thesis,user);
            System.out.println("delete2"+i+"thesis"+thesis.getThesisId());
        }

        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
