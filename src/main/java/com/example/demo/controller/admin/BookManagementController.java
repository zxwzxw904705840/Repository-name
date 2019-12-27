
package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.*;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 管理员——论文管理
 */
@Controller
public class BookManagementController {


    /**
     * 页面 表格
     *
     * @param request
     * @return 输入：limit(int)，offset(int),
     * <p>
     * projectName(String)
     * ps: 后面一个可能为空
     * 支持项目名称模糊查询
     * 返回：rows 列表
     * total 总数
     */
    @ResponseBody
    @RequestMapping("/FindBookList")
    public Map<String, Object> FindBookList(HttpServletRequest request, HttpSession session) throws ParseException {
        System.out.println("FindBookList");
        UserEntity operator = (UserEntity) session.getAttribute("user");

        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize")); //输入
        int offset = limit * (pageNumber - 1);//输入
        String bookName = request.getParameter("bookName");//输入


        Map<String, Object> params = new HashMap<>();
        params.put("bookName", bookName);
        params.put("limit", limit);
        params.put("offset", offset);

        System.out.println("params" + params);

        List<BookEntity> lists = new ArrayList<>();
        for (int i = offset; i < offset + 10; i++) {
            BookEntity temp = new BookEntity();
            temp.setBookId("id" + i);
            temp.setBookName("title" + i);
            UserEntity author1 = new UserEntity();
            author1.setUserId("userId1" + i);
            author1.setUserName("userName1" + i);
            UserEntity author2 = new UserEntity();
            author1.setUserId("userId2" + i);
            author1.setUserName("userName2" + i);
            UserEntity author3 = new UserEntity();
            author1.setUserId("userId3" + i);
            author1.setUserName("userName3" + i);
            temp.setAuthor1(author1);
            temp.setAuthor2(author2);
            temp.setAuthor3(author3);

            temp.setCreativeNature(Const.BookCreativeNature.ARRANGE);
            temp.setBookInformation("brief"+i);
            String timeStr = "2019-12-25";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date time = sdf.parse(timeStr);
            temp.setBookPublishDate(time);

            if (i % 2 == 0) {
                temp.setBookStatus(Const.BookStatus.NORMAL);
                temp.setBookPublishStatus(Const.BookPublishStatus.PUBLISHED);
            } else {
                temp.setBookStatus(Const.BookStatus.REVIEWING);
                temp.setBookPublishStatus(Const.BookPublishStatus.UNPUBLISHED);
            }

            lists.add(temp);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("rows", lists);
        result.put("total", 100); //整个表的总数*/


        return result;

    }

    /**
     *
     * 表单内 通过升级审核
     * @param bookId
     * @return
     *
     * 输入：userId
     * 输出：成功/失败
     */

    @ResponseBody
    @RequestMapping("/AgreeBookUpdate")
    public String AgreeBookUpdate(String bookId,HttpSession session) {
        System.out.println("AgreeBookUpdate");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        BookEntity bookEntity = new BookEntity(bookId);
        bookEntity.setBookStatus(Const.BookStatus.NORMAL);
        //调用sevice


        Result result = new Result(true,"AgreeUserUpdate");

        return  result.toString();
    }


    /**
     * 页面  批量删除
     *
     * @param ids
     * @return
     *
     * 输入： thesisId []
     *
     * 返回： 成功/失败
     */
    @ResponseBody
    @RequestMapping("/RemoveBook")
    public String removeBook(String[] ids,HttpSession session) {
        System.out.println("RemoveBook");
        UserEntity operator = (UserEntity) session.getAttribute("user");

        Result result = new Result(true,"delete");
        return  result.toString();
    }

}
