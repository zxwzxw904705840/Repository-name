package com.example.demo.controller.admin;

import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 管理员——论文管理
 *
 */
@Controller
public class ThesisManagementController {


    /**
     * 页面 表格
     * @param request
     * @return
     *
     * 输入：limit(int)，offset(int),
     *
     *      projectName(String)
     *      ps: 后面一个可能为空
     *           支持项目名称模糊查询
     * 返回：rows 列表
     *      total 总数
     */
    @ResponseBody
    @RequestMapping("/FindThesisList")
    public Map<String, Object> findTBlist(HttpServletRequest request, HttpSession session) throws ParseException {
        System.out.println("FindThesisList");
        UserEntity userInfo = (UserEntity) session.getAttribute("userInfo");

        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize")); //输入
        int offset = limit * (pageNumber - 1);//输入
        String thesisTitle = request.getParameter("thesisTitle");//输入


        Map<String, Object> params = new HashMap<>();
        params.put("projectName", thesisTitle);
        params.put("limit", limit);
        params.put("offset", offset);

        System.out.println("params" + params);

        List<ThesisEntity> lists = new ArrayList<>();
        for(int i =offset;i<offset+10;i++){
            ThesisEntity temp = new ThesisEntity();
            temp.setThesisId("id"+i);
            temp.setThesisTitle("title"+i);
            UserEntity  author1 = new UserEntity();
            author1.setUserId("userId1"+i);
            author1.setUserName("userName1"+i);
            UserEntity  author2 = new UserEntity();
            author1.setUserId("userId2"+i);
            author1.setUserName("userName2"+i);
            UserEntity  author3 = new UserEntity();
            author1.setUserId("userId3"+i);
            author1.setUserName("userName3"+i);
            temp.setAuthor1(author1);
            temp.setAuthor2(author2);
            temp.setAuthor3(author3);
            temp.setJournal("journal"+i);
            temp.setVolume("Volume"+i);
            temp.setUrl("url"+i);
            temp.setPages(i);
            temp.setPrivacy(Const.ThesisPrivacy.PRIVATE);
            temp.setStatus(Const.ThesisStatus.DELETED);
            lists.add(temp);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("rows", lists);
        result.put("total",100); //整个表的总数*/



        return result;

    }

}
