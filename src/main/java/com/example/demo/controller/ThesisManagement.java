package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.ThesisEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThesisManagement {
    /**
     * 用户个人论文展示列表界面
     * @return
     */
    @RequestMapping("/MyThesis")
    public  String MyThesis(){

        return "MyThesis";
    }

    /**
     * 获取用户所有对应的论文信息list，方便传数据到table里显示
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("../../MyThesis/findTBlist")
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

        //   List<ThesisEntity> lists = thesisService.findTBlist(params);

        //     System.out.println("lists.size" + lists.size());

        //     session.setAttribute("textbooklist", all);
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


        JSONObject result=new JSONObject();
        result.put("message","success");
        return "AddThesis";
    }

    /**提交新增论文信息，论文ID就是文章编号，属于必填项
     *
     * @param request
     * @return
     */
    @RequestMapping("/AddThesisSubmit")
    public String submit(HttpServletRequest request) {
        String userid=request.getSession().getAttribute("userId").toString();
        String title=request.getParameter("input1");
        String thesisId=request.getParameter("input2");
        String author1="";
        String author2="";
        String author3="";
        String journal="";
        String volume="";
        String pages="";
        String url="";
        if(request.getParameter("input3")!=null){
            author1=request.getParameter("input3");
        }
        if(request.getParameter("input4")!=null){
            author2=request.getParameter("input4");
        }
        if(request.getParameter("input5")!=null){
            author3=request.getParameter("input5");
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
    public String deleteThesis(HttpServletRequest request) {


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
