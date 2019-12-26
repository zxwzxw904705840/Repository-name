package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.BookEntity;
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

import java.util.ArrayList;

import java.util.List;

@Controller
public class CopyrightManagement {

    @Autowired
    UserService userService;

    @RequestMapping("/CopyrightDetail")
    public  String CopyrightDetail(){

        return "CopyrightDetail";
    }
    /**
     * 删除
     * @return
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public String deleteAll(String checkList){

        System.out.println("==>"+checkList);

        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();

        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }

    //    resourcesService.deleteAll(ids);

        return "success";
    }
    /**
     * 用户个人软件著作权展示列表界面
     * @return
     */
    @RequestMapping("/MySoftwareCopyright")
    public  String MySoftwareCopyright(HttpServletRequest request, Model model){
        String userid=request.getSession().getAttribute("userId").toString();

        List<BookEntity> bookList=new ArrayList<>();

        UserEntity user=new UserEntity();
        user.setUserName("叶元卯");

        //临时假数据
        for(int i=0;i<15;i++){
            BookEntity booktmp=new BookEntity();
            booktmp.setAuthor1(user);

            booktmp.setBookId("100"+i*2+"4-2-85"+i);
            if(i/2!=0)
                booktmp.setBookName("神经网络遗传算法著作专利");
            if(i/2==0)
                booktmp.setBookName("生物神经概率选择著作专利");
            bookList.add(booktmp);
        }
        model.addAttribute("bookList",bookList);
        return "MySoftwareCopyright";
    }

    /**
     * 获取用户所有对应的论文信息list，方便传数据到table里显示
     * @param session
     * @param request
     * @param response
     * @return
     */
    //暂时放弃这个函数了，不用管它
  /**  @ResponseBody
    @RequestMapping("../../findTBlist")
    public Map<String, Object> findTBlist(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("findTBlist");
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();  //这两句是做什么的？

        String bookId = request.getParameter("bookId");
        String bookName = request.getParameter("bookName");

        if (bookName != "" && bookName != null) {
            params.put("bookName", bookName);
        }
        if (bookId != "" && bookId!= null) {
            params.put("bookId", bookId);
        }
        System.out.println(params);

        //  List<ThesisEntity> all = thesisService.findTBlist(params);
        //   session.setAttribute("vTbList", all);

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
**/
    //跳转到添加软件著作权界面
    @RequestMapping("/AddCopyright")
    public String AddCopyright(HttpServletRequest request) {
        String userid=request.getSession().getAttribute("userId").toString();
        return "AddCopyright";
    }

    /**提交新增软件著作权信息，著作权ID是必填项
     *
     * @param request
     * @return
     */
    @RequestMapping("/AddCopyrightSubmit")
    public String submits(HttpServletRequest request) {
        BookEntity book =new BookEntity();
        String userid=request.getSession().getAttribute("userId").toString();
        String title=request.getParameter("input1");
        String bookId=request.getParameter("input2");
        String author1name="";
        String author2name="";
        String author3name="";
        String bookStatustry="";
        String bookPublishDatetry="";


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
            bookPublishDatetry=request.getParameter("input6");
        }
        if(request.getParameter("input7")!=null){
            bookStatustry=request.getParameter("input7");
        }


        book.setBookId(bookId);
        book.setBookName(title);
        book.setAuthor1(user1);
        book.setAuthor2(user2);
        book.setAuthor3(user3);

       // book.setPrivacy();

        System.out.println("/AddCopyrightSubmit userid:"+userid+" privacy："+" title:"+title);
        return "AddCopyrightSubmit";
    }

    //修改某篇论文信息
    @ResponseBody
    @RequestMapping("/editCopyright")
    public String editCopyright(HttpServletRequest request) {
        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    //批量删除论文
    @ResponseBody
    @RequestMapping("/deleteCopyright")
    public String deleteCopyright(HttpServletRequest request,@RequestParam("params")List<String> copyrightidlist) {
        System.out.println("/MyCopyrightidlist:"+copyrightidlist);

        
        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
