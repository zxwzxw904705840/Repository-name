package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
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
        UserEntity operator = (UserEntity) session.getAttribute("user");

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
            if(i%2==0){
                temp.setStatus(Const.ThesisStatus.NORMAL);
            }else {
                temp.setStatus(Const.ThesisStatus.REVIEWING);
            }

            lists.add(temp);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("rows", lists);
        result.put("total",100); //整个表的总数*/



        return result;

    }
    @RequestMapping(value = "/SearchAuthor")
    public String SearchMember(Model model, String projectId, String userId, HttpSession session) {
        UserEntity operator = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = new UserEntity(userId);

        userEntity.setUserId(userId);
        userEntity.setUserName("2323");
        userEntity.setPhone("22222PHONE");
        userEntity.setEmail("22222MAIL");
        userEntity.setInstitute(new InstituteEntity("12", "图书馆"));

        model.addAttribute("userInfo", userEntity);
        return "fragment::addAuthorFragment";
    }
    /**
     * 模态框  新增项目-提交
     *
     * @param thesisInfos
     * @return 输入：thesisInfos
     * projectInfos 包括
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/AddThesisM")
    public String AddThesisM(@RequestBody JSONObject thesisInfos, HttpSession session) {
        System.out.println("AddThesisM");
        System.out.println(thesisInfos);
        try {
            ThesisEntity thesisEntity = new ThesisEntity();
            if(!thesisInfos.getString("author3id").equals("")){
                UserEntity userEntity = new UserEntity(thesisInfos.getString("author3id"));
                thesisEntity.setAuthor3(userEntity);
            }
            if(!thesisInfos.getString("author2id").equals("")){
                UserEntity userEntity = new UserEntity(thesisInfos.getString("author2id"));
                thesisEntity.setAuthor2(userEntity);
            }
            if(!thesisInfos.getString("author1id").equals("")){
                UserEntity userEntity = new UserEntity(thesisInfos.getString("author1id"));
                thesisEntity.setAuthor1(userEntity);
            }
            thesisEntity.setVolume(thesisInfos.getString("volume"));
            thesisEntity.setJournal(thesisInfos.getString("journal"));
            thesisEntity.setPages(thesisInfos.getInteger("pages"));
            thesisEntity.setThesisTitle(thesisInfos.getString("thesisTitle"));
            thesisEntity.setUrl(thesisInfos.getString("url"));
            thesisEntity.setPrivacy(Const.ThesisPrivacy.valueOf(thesisInfos.getString("privacy")));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Result result = new Result(true, "AddThesisM");
        return result.toString();
    }


    /**
     * 模态框 修改论文题目
     *
     * @param thesisId
     * @param thesisTitle
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetThesisTitle")
    public String setThesisTitle(String thesisId, String thesisTitle, HttpSession session) {
        System.out.println("SetThesisTitle");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setThesisTitle(thesisTitle);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }


    /**
     * 模态框 修改论文期刊
     *
     * @param thesisId
     * @param journal
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetJournal")
    public String setJournal(String thesisId, String journal, HttpSession session) {
        System.out.println("SetJournal");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setJournal(journal);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     * 模态框 修改论文期、卷
     *
     * @param thesisId
     * @param volume
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetVolume")
    public String setVolume(String thesisId, String volume, HttpSession session) {
        System.out.println("SetVolume");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setVolume(volume);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     * 模态框 修改论url
     *
     * @param thesisId
     * @param url
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetUrl")
    public String setUrl(String thesisId, String url, HttpSession session) {
        System.out.println("SetUrl");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setUrl(url);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }



    /**
     * 模态框 修改论文页面长度
     *
     * @param thesisId
     * @param pages
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetPages")
    public String setPages(String thesisId, Integer pages, HttpSession session) {
        System.out.println("SetPages");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setPages(pages);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     * 模态框 修改论文公开性
     *
     * @param thesisId
     * @param privacy
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetPrivacy")
    public String setPrivacy(String thesisId, @RequestParam(value = "privacy") Const.ThesisPrivacy privacy, HttpSession session) {
        System.out.println("SetPrivacy");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setPrivacy(privacy);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }


    /**
     * 模态框 修改论文第一作者
     *
     * @param userId
     * @param thesisId
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetAuthorFirst")
    public String setAuthorFirst(String thesisId, String userId, HttpSession session) {
        System.out.println("SetAuthorFirst");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        UserEntity author1 = new UserEntity(userId);
        thesisEntity.setAuthor1(author1);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     * 模态框 修改论文第二作者
     *
     * @param userId
     * @param thesisId
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetAuthorSecond")
    public String setAuthorSecond(String thesisId, String userId, HttpSession session) {
        System.out.println("SetAuthorSecond");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        UserEntity author2 = new UserEntity(userId);
        thesisEntity.setAuthor2(author2);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     * 模态框 修改论文第三作者
     *
     * @param userId
     * @param thesisId
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetAuthorThird")
    public String setAuthorThird(String thesisId, String userId, HttpSession session) {
        System.out.println("SetAuthorThird");
        /*   System.out.println(thesisId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        UserEntity author3 = new UserEntity(userId);
        thesisEntity.setAuthor3(author3);


        Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }

    /**
     *
     * 表单内 通过升级审核
     * @param thesisId
     * @return
     *
     * 输入：userId
     * 输出：成功/失败
     */

    @ResponseBody
    @RequestMapping("/AgreeThesisUpdate")
    public String agreeThesisUpdate(String thesisId,HttpSession session) {
        System.out.println("AgreeThesisUpdate");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ThesisEntity thesisEntity = new ThesisEntity(thesisId);
        thesisEntity.setStatus(Const.ThesisStatus.NORMAL);
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
    @RequestMapping("/RemoveThesis")
    public String removeThesis(String[] ids,HttpSession session) {
        System.out.println("RemoveThesis");
        UserEntity operator = (UserEntity) session.getAttribute("user");

        Result result = new Result(true,"delete");
        return  result.toString();
    }

}
