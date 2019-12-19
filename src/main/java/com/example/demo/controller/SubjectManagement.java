package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.Const;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SubjectManagement {

    @GetMapping("/subjectManagement")
    public  String index(){

        return "subjectManagement";
    }

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
    @RequestMapping("/FindProjectList")
    public Map<String, Object> findTBlist(HttpServletRequest request) {
        System.out.println("FindUserList");


        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize")); //输入
        int offset = limit * (pageNumber - 1);//输入
         String projectName = request.getParameter("projectName");//输入


        Map<String, Object> params = new HashMap<>();
        params.put("projectName", projectName);
        params.put("limit", limit);
        params.put("offset", offset);
        System.out.println("params" + params);

        Map<String, Object> result = new HashMap<>();



        return result;

    }

    /**
     * 页面  批量删除
     *
     * @param ids
     * @return
     *
     * 输入： projectId []
     *
     * 返回： 成功/失败
     */
    @ResponseBody
    @RequestMapping("/RemoveProject")
    public String RemoveUser(String[] ids) {
        System.out.println("RemoveProject");

        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  新增项目-提交
     * @param projectInfos
     * @return
     *
     * 输入：projectInfos
     *      projectInfos 包括
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/AddProject")
    public String AddUser(@RequestBody JSONObject projectInfos ) {
        System.out.println("AddProject");
        //  System.out.println(userInfos);

        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框 修改研究人员名称
     * @param projectId
     * @param userName
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetUserName")
    public String SetUserName(String projectId,String userName) {
        System.out.println("SetUserName");

        System.out.println(projectId+":::"+userName);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }
    /**
     * 模态框 修改研究人员所属学院
     * @param projectId
     * @param instituteId
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetInstituteId")
    public String SetInstituteId(String projectId,String instituteId) {
        System.out.println("SetInstituteId");

        System.out.println(projectId+":::"+instituteId);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框 修改项目名称
     * @param projectId
     * @param projectName
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectName")
    public String SetProjectName(String projectId,String projectName) {
        System.out.println("SetProjectName");
        System.out.println(projectId+":::"+projectName);

        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }
    /**
     * 模态框 修改项目类型
     * @param projectId
     * @param projectType
     * @return
     *
     * 输出：成功/失败
     *
     */
    @ResponseBody
    @RequestMapping("/SetProjectType")
    public String projectType(String projectId,@RequestParam(value="projectType") Const.ProjectType projectType) {
        System.out.println("SetProjectType");

        System.out.println(projectId+":::"+projectType);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }
    /**
     * 模态框 修改项目负责人
     * @param projectId
     * @param userId
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectManager")
    public String SetProjectManager(String projectId,String userId) {
        System.out.println("SetProjectManager");

        System.out.println(projectId+":::"+userId);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框 修改项目等级
     * @param projectId
     * @param projectLevel
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectLevel")
    public String SetProjectLevel(String projectId, @RequestParam(value="projectLevel") Const.ProjectLevel projectLevel) {
        System.out.println("SetProjectLevel");

        System.out.println(projectId+":::"+projectLevel);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框 修改项目进度
     * @param projectId
     * @param projectProgress
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectProgress")
    public String SetProjectProgress(String projectId,@RequestParam(value="projectProgress") Const.ProjectProgress projectProgress) {

        System.out.println("SetProjectProgress");

        System.out.println(projectId+":::"+projectProgress);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框 修改项目来源单位
     * @param projectId
     * @param projectSource
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectSource")
    public String SetProjectSource(String projectId,String projectSource) {
        System.out.println("SetProjectSource");

        System.out.println(projectId+":::"+projectSource);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


    /**
     * 模态框  修改项目立项日期
     * @param projectId
     * @param projectEstablishDate
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectLevel")
    public String SetProjectLevel(String projectId, Date projectEstablishDate) {
        System.out.println("SetProjectLevel");

        System.out.println(projectId+":::"+projectEstablishDate);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  修改项目计划完成日期
     * @param projectId
     * @param projectPlannedDate
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectPlannedDate")
    public String projectPlannedDate(String projectId, Date projectPlannedDate) {
        System.out.println("projectPlannedDate");

        System.out.println(projectId+":::"+projectPlannedDate);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  修改开始时间
     * @param projectId
     * @param projectLauchDate
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectLauchDate")
    public String projectLauchDate(String projectId, Date projectLauchDate) {
        System.out.println("projectPlannedDate");

        System.out.println(projectId+":::"+projectLauchDate);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


    /**
     * 模态框  修改结项日期
     * @param projectId
     * @param projectFinishDate
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectFinishDate")
    public String projectFinishDate(String projectId, Date projectFinishDate) {
        System.out.println("projectFinishDate");

        System.out.println(projectId+":::"+projectFinishDate);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  修改合同经费
     * @param projectId
     * @param projectFund
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectFinishDate")
    public String projectLauchDate(String projectId, Integer projectFund) {
        System.out.println("projectFinishDate");

        System.out.println(projectId+":::"+projectFund);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }



    /**
     * 模态框  修改项目来源类别
     * @param projectId
     * @param projectSourceType
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectSourceType")
    public String projectSourceType(String projectId, @RequestParam(value="projectSourceType") Const.ProjectSourceType projectSourceType) {
        System.out.println("projectSourceType");

        System.out.println(projectId+":::"+projectSourceType);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  修改研究类别
     * @param projectId
     * @param projectResearchType
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/projectResearchType")
    public String projectResearchType(String projectId,@RequestParam(value="projectResearchType") Const.ProjectResearchType projectResearchType) {
        System.out.println("projectSourceType");

        System.out.println(projectId+":::"+projectResearchType);


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }

    /**
     * 模态框  添加负责人时，根据 名称模糊查询 或者 userid 查询
     * @param input
     * @param flag
     * @return
     *
     * 输入：flag 分辨是根据名称还是userid 查询
     *
     * 输出：userEnity 列表，包括 userid 、username
     */
    @ResponseBody
    @RequestMapping("/FindUserByAll")
    public String FindUserByAll(String input, String flag) {
        System.out.println("FindUserByAll");

   if(flag.equals("userName")){

   }else if(flag.equals("userId")){

   }


        JSONObject result=new JSONObject();
        result.put("message","success");
        return result.toString();
    }


}
