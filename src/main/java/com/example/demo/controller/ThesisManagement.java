package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        model.addAttribute("thesisList",thesisList);
        return "MyThesis";
    }

    /**
     * 获取用户所有对应的论文信息list，方便传数据到table里显示
     * @param session
     * @param request
     * @param response
     * @return
     */
    //暂时放弃这个函数了，不用管它
    @ResponseBody
    @RequestMapping("../../findTBlist")
    public Map<String, Object> findTBlist(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("findTBlist");
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        int offset = pageSize * (pageNumber - 1);

        String thesisId = request.getParameter("thesisId");
        String thesisTitle = request.getParameter("thesisTile");

        if (thesisTitle != "" && thesisTitle != null) {
            params.put("thesisTitle", thesisTitle);
        }
        if (thesisId != "" && thesisId!= null) {
            params.put("thesisId", thesisId);
        }
        System.out.println(params);

        //  List<ThesisEntity> all = thesisService.findTBlist(params);

        //   session.setAttribute("vTbList", all);

        params.put("limit", pageSize);
        params.put("offset", offset);

        System.out.println("params" + params);

//           List<ThesisEntity> lists = thesisService.findTBlist(params);
//
//             System.out.println("lists.size" + lists.size());
//
//             session.setAttribute("textbooklist", all);
//
//        result.put("rows", lists);
//        result.put("total", all.size());
//
//        System.out.println("all.size()" + all.size());
        //   System.out.println("result" + result);

        return result;
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
        String userid=request.getSession().getAttribute("userId").toString();
        String title=request.getParameter("input1");
        String thesisId=request.getParameter("input2");
        String author1name="";
        String author2name="";
        String author3name="";
        String journal="";
        String volume="";
        String pages="";
        String url="";

        UserEntity user1=new UserEntity();
        UserEntity user2=new UserEntity();
        UserEntity user3=new UserEntity();

        user1=userService.findByUserNameLike(author1name).get(0);
        user2=userService.findByUserNameLike(author2name).get(0);
        user3=userService.findByUserNameLike(author3name).get(0);

        if(request.getParameter("input3")!=null){
            author1name=request.getParameter("input3");
        }
        if(request.getParameter("input4")!=null){
            author2name=request.getParameter("input4");
        }
        if(request.getParameter("input5")!=null){
            author3name=request.getParameter("input5");
        }
        if(request.getParameter("input6")!=null){
            journal=request.getParameter("input6");
        }
        if(request.getParameter("input7")!=null){
            volume=request.getParameter("input7");
        }
        if(request.getParameter("input8")!=null){
            pages=request.getParameter("input8");
        }
        if(request.getParameter("input10")!=null){
            url=request.getParameter("input10");
        }
        String privacy=request.getParameter("optionsRadiosinline");

        thesis.setThesisId(thesisId);
        thesis.setThesisTitle(title);
        thesis.setAuthor1(user1);
        thesis.setAuthor2(user2);
        thesis.setAuthor3(user3);
        thesis.setJournal(journal);
        thesis.setVolume(volume);
        thesis.setUrl(url);
        thesis.setPages(Integer.parseInt(pages));
       // thesis.setPrivacy();

        System.out.println("/AddThesisSubmit userid:"+userid+" privacy："+privacy+" title:"+title);
        return "MyThesis";
    }

    //修改某篇论文信息
    @ResponseBody
    @RequestMapping("/editThesis")
    public String editThesis(HttpServletRequest request) {
        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    //批量删除论文
    @ResponseBody
    @RequestMapping("/deleteThesis")
    public String deleteThesis(HttpServletRequest request,@RequestParam("params")List<String> thsisidlist) {
        System.out.println("/MyThesisthsisidlist:"+thsisidlist);

        
        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
