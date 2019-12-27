package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.DataCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;







@Controller
public class CopyrightManagement {

    @Autowired
    UserService userService;



    /**
     * 用户个人软件著作权展示列表界面
     * @return
     */
    @RequestMapping("/MySoftwareCopyright")
    public  String MySoftwareCopyright(HttpServletRequest request, Model model){
        String userid=request.getSession().getAttribute("userId").toString();
        List<BookEntity> bookList=new ArrayList<>();

        UserEntity user=new UserEntity();

        System.out.println("userid add:"+userid);
        bookList=userService.findAllBookByAuthorId(userid);
        //先把url中的“/”都替换成“%2F”
        for(int i=0;i<bookList.size();i++){
            String str=bookList.get(i).getBookId();
            String str2=str.replace("/", "--2F-2F-");
            bookList.get(i).setBookId(str2);;
        }

        model.addAttribute("bookList",bookList);
        return "MySoftwareCopyright";


    }


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
        UserEntity user=new UserEntity();
        String userid=request.getSession().getAttribute("userId").toString();
        //user=(UserEntity)userService.getUserById(userid).getObject(userid);
        user=userService.getUserById(userid);

        String title=request.getParameter("input1");
        String bookId=request.getParameter("input2");
        String author1name="";
        String author2name="";
        String author3name="";
        String bookInformationtry="";
        String bookPublishDatetry="";
        String zero=String.valueOf(0);


        UserEntity user1=new UserEntity();
        UserEntity user2=new UserEntity();
        UserEntity user3=new UserEntity();

        book.setBookId(bookId);
        book.setBookName(title);

        book.setBookPublishStatus(Const.BookPublishStatus.class.getEnumConstants()[1]);
        book.setBookStatus(Const.BookStatus.class.getEnumConstants()[1]);
        book.setCreativeNature(Const.BookCreativeNature.class.getEnumConstants()[1]);

        if(request.getParameter("input3")!=null&&request.getParameter("input3")!=""){
            author2name=request.getParameter("input3");
            user1=userService.findByUserNameLike(author2name).get(0);
            book.setAuthor1(user1);
        }
        if(request.getParameter("input4")!=null&&request.getParameter("input4")!=""){
            author2name=request.getParameter("input4");
            user2=userService.findByUserNameLike(author2name).get(0);
            book.setAuthor2(user2);
        }
        if(request.getParameter("input5")!=null&&request.getParameter("input5")!=""){
            author3name=request.getParameter("input5");
            user3=userService.findByUserNameLike(author3name).get(0);
            book.setAuthor3(user3);
        }
        if(request.getParameter("input6")!=null&&request.getParameter("input6")!=""){
            bookPublishDatetry=request.getParameter("input6");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                //使用SimpleDateFormat的parse()方法生成Date
                Date date = sf.parse(bookPublishDatetry);
                //打印Date
                System.out.println(date);
                book.setBookPublishDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if(request.getParameter("input7")!=null&&request.getParameter("input7")!=""){
            bookInformationtry=request.getParameter("input7");
            book.setBookInformation(bookInformationtry);
        }

       // book.setPrivacy();
        System.out.println("/AddThesisSubmit userid:"+userid+" title:"+title+" status");
        System.out.println("result:"+userService.addBook(book,user));
        return "redirect:MySoftwareCopyright";
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
    public String deleteCopyright(HttpServletRequest request,@RequestParam("params")List<String> bookidlist) {
        String userid=request.getSession().getAttribute("userId").toString();
        UserEntity user=new UserEntity();
        //user=(UserEntity)userService.getUserById(userid).getObject(userid);
        user=userService.getUserById(userid);
        System.out.println("/deleteCopyright:"+bookidlist);
        for(int i=0;i<bookidlist.size();i++){
            BookEntity book=new BookEntity();
            book=userService.findByBookId(bookidlist.get(i));
            System.out.println("delete "+i+"book"+book.getBookId());
            userService.deleteBook(book,user);
            System.out.println("delete2"+i+"book"+book.getBookId());
        }

        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
