package com.example.demo.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.Entity.FileEntity;
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.controller.ExcelOpt;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 管理员——项目管理
 */
@Controller
public class ProjectManagementController {
    @Autowired
    private ProjectManagementService projectManagementService;

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
    @RequestMapping("/FindProjectList")
    public Map<String, Object> findTBlist(HttpServletRequest request, HttpSession session) throws ParseException {
        System.out.println("FindProjectList");

        UserEntity operator = (UserEntity) session.getAttribute("user");

       // UserEntity operator = new UserEntity("222");

        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize")); //输入
        int offset = limit * (pageNumber - 1);//输入
        String projectName = request.getParameter("projectName");//输入
        String projectCode = request.getParameter("projectCode");//输入
        String instituteId = request.getParameter("instituteId");//输入
        String projectTyp = request.getParameter("projectTyp");//输入
        String projectLevel = request.getParameter("projectLevel");//输入
        String rojectResearchType = request.getParameter("rojectResearchType");//输入
        String projectEstablishDates = request.getParameter("projectEstablishDate");//输入
        ProjectEntity project = new ProjectEntity(projectName);
        Result result =null;
        if(projectName.equals("")){
            result=projectManagementService.findAllProject( limit,  offset,  operator);
        }else {
            result=projectManagementService.findAllProjectByProjectNameLike( limit,  offset, project, operator);
        }
        if (instituteId.equals("ALL")) {

        }
        if (projectTyp.equals("ALL")) {

        }
        if (projectLevel.equals("ALL")) {

        }
        if (rojectResearchType.equals("ALL")) {

        }
        Date projectEstablishDate = new Date();
        if (!projectEstablishDates.equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            projectEstablishDate = df.parse(projectEstablishDates);


        }



       /* List<ProjectEntity> lists = new ArrayList<>();
        for (int i = offset; i < offset + 10; i++) {
            ProjectEntity temp = new ProjectEntity();
            temp.setProjectId("id" + i);

            Date time = new Date();

            String timeStr = "2019-12-25";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            time = sdf.parse(timeStr);
            temp.setProjectEstablishDate(time);
            temp.setProjectCode("code" + i);
            temp.setProjectName("projectName" + i);
            temp.setProjectFund(100);
            temp.setProjectLevel(Const.ProjectLevel.CITY);
            temp.setProjectProcess(Const.ProjectProgress.COMPLETE);
            UserEntity user = new UserEntity();
            user.setUserId("userId" + i);
            user.setUserName("userName" + i);
            temp.setProjectManager(user);
            lists.add(temp);
        }
*/
        Map<String, Object> param = new HashMap<>();
         param.put("rows", result.getObject("rows"));
        param.put("total", result.getObject("total")); //整个表的总数*/
        /*param.put("rows", lists);
        param.put("total",100); //整个表的总数*/
        return param;

    }

    /**
     * 页面  批量删除
     *
     * @param ids
     * @return 输入： projectId []
     * <p>
     * 返回： 成功/失败
     */
    @ResponseBody
    @RequestMapping("/RemoveProject")
    public String RemoveUser(String[] ids, HttpSession session) {
        System.out.println("RemoveProject");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ArrayList<ProjectEntity> lists = new ArrayList<>();
        for(String id :ids){
           ProjectEntity projectEntity =  new ProjectEntity(id);
            lists.add(projectEntity);
        }
        Result result= projectManagementService.deleteAllProjectsByProjectId(lists,operator);
      //  Result result = new Result(true, "ADD");
        return result.toString();
    }

    /**
     * 模态框  新增项目-提交
     *
     * @param projectInfos
     * @return 输入：projectInfos
     * projectInfos 包括
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/AddProject")
    public String AddUser(@RequestBody JSONObject projectInfos, HttpSession session) {
        System.out.println("AddProject");
        System.out.println(projectInfos);
        ProjectEntity projectEntity = new ProjectEntity();
           UserEntity operator = (UserEntity) session.getAttribute("user");
       // UserEntity operator = new UserEntity ("222");
        projectEntity.setProjectName((projectInfos.getString("projectName")));
        projectEntity.setProjectResearchType(Const.ProjectResearchType.valueOf(projectInfos.getString("projectResearchType")));
        projectEntity.setProjectCode(projectInfos.getString("projectCode"));
        projectEntity.setProjectLevel(Const.ProjectLevel.valueOf(projectInfos.getString("projectLevel")));
        projectEntity.setProjectProcess(Const.ProjectProgress.valueOf(projectInfos.getString("projectProgress")));
        projectEntity.setProjectType(Const.ProjectType.valueOf(projectInfos.getString("projectType")));
        projectEntity.setProjectSourceType(Const.ProjectSourceType.valueOf(projectInfos.getString("projectSourceType")));
        UserEntity temp = new UserEntity(projectInfos.getString("projectManagerId"));
        projectEntity.setProjectManager(temp);
        projectEntity.setProjectSource(projectInfos.getString("projectSource"));
        projectEntity.setProjectFund(projectInfos.getInteger("projectFund"));
        projectEntity.setProjectEstablishDate(projectInfos.getDate("projectEstablishDate"));
        projectEntity.setProjectFinishDate(projectInfos.getDate("projectFinishDate"));
        projectEntity.setProjectLaunchDate(projectInfos.getDate("projectLaunchDate"));
        projectEntity.setProjectPlannedDate(projectInfos.getDate("projectPlannedDate"));
        System.out.println(projectEntity.getProjectEstablishDate().toString() + "123231213");
        Result result = projectManagementService.addProject(projectEntity,operator);
        System.out.println(result);
        return result.toString();
    }

    /**
     * 模态框 修改项目代号
     *
     * @param projectId
     * @param projectCode
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectCode")
    public String setProjectCode(String projectId, String projectCode, HttpSession session) {
        System.out.println("SetProjectName");
        /*   System.out.println(projectId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectCode(projectCode);



      //  Result result= projectManagementService.deleteAllProjectsByProjectId(lists,operator);


       Result result = new Result(true, "SetProjectCode");
        return result.toString();
    }


    /**
     * 模态框 修改项目名称
     *
     * @param projectId
     * @param projectName
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectName")
    public String SetProjectName(String projectId, String projectName, HttpSession session) {
        System.out.println("SetProjectName");
        /*   System.out.println(projectId+":::"+projectName);*/
        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectName(projectName);

        Result result= projectManagementService.updateProjectSetProjectName(projectEntity,operator);

     //   Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框 修改项目类型
     *
     * @param projectId
     * @param projectType
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectType")
    public String projectType(String projectId, @RequestParam(value = "projectType") Const.ProjectType projectType, HttpSession session) {
        System.out.println("SetProjectType");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectType(projectType);

        Result result= projectManagementService.updateProjectSetProjectType(projectEntity,operator);

      //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框 修改项目负责人
     *
     * @param projectId
     * @param userId
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectManager")
    public String SetProjectManager(String projectId, String userId, HttpSession session) {
        System.out.println("SetProjectManager");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        UserEntity temp = new UserEntity(userId);
        projectEntity.setProjectManager(temp);

        Result result= projectManagementService.updateProjectSetProjectManager(projectEntity,operator);

     //   Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框 修改项目等级
     *
     * @param projectId
     * @param projectLevel
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectLevel")
    public String SetProjectLevel(String projectId, @RequestParam(value = "projectLevel") Const.ProjectLevel projectLevel, HttpSession session) {
        System.out.println("SetProjectLevel");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectLevel(projectLevel);


        Result result= projectManagementService.updateProjectSetProjectLevel(projectEntity,operator);

//        Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框 修改项目进度
     *
     * @param projectId
     * @param projectProgress
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectProgress")
    public String SetProjectProgress(String projectId, @RequestParam(value = "projectProgress") Const.ProjectProgress projectProgress, HttpSession session) {

        System.out.println("SetProjectProgress");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectProcess(projectProgress);

        Result result= projectManagementService.updateProjectSetProjectProgress(projectEntity,operator);

      //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框 修改项目来源单位
     *
     * @param projectId
     * @param projectSource
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectSource")
    public String SetProjectSource(String projectId, String projectSource, HttpSession session) {
        System.out.println("SetProjectSource");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectSource(projectSource);

        Result result= projectManagementService.updateProjectSetProjectSource(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }


    /**
     * 模态框  修改项目立项日期
     *
     * @param projectId
     * @param projectEstablishDates
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectEstablishDate")
    public String projectEstablishDate(String projectId, String projectEstablishDates, HttpSession session) throws ParseException {
        System.out.println("projectEstablishDate");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);


        if (projectEstablishDates != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date projectEstablishDate = df.parse(projectEstablishDates);
            projectEntity.setProjectEstablishDate(projectEstablishDate);
        }

        Result result= projectManagementService.updateProjectSetProjectEstablishDate(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框  修改项目计划完成日期
     *
     * @param projectId
     * @param projectPlannedDates
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectPlannedDate")
    public String projectPlannedDate(String projectId, String projectPlannedDates, HttpSession session) throws ParseException {
        System.out.println("SetProjectPlannedDate");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);


        if (projectPlannedDates != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date projectPlannedDate = df.parse(projectPlannedDates);
            projectEntity.setProjectPlannedDate(projectPlannedDate);
        }
        Result result= projectManagementService.updateProjectSetProjectPlannedDate(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框  修改开始时间
     *
     * @param projectId
     * @param projectLauchDates
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectLauchDate")
    public String projectLauchDate(String projectId, String projectLauchDates, HttpSession session) throws ParseException {
        System.out.println("SetProjectLauchDate");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);

        if (projectLauchDates != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date projectLauchDate = df.parse(projectLauchDates);
            projectEntity.setProjectLaunchDate(projectLauchDate);
        }

        Result result= projectManagementService.updateProjectSetProjectLaunchDate(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");

        return result.toString();
    }


    /**
     * 模态框  修改结项日期
     *
     * @param projectId
     * @param projectFinishDates
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectFinishDate")
    public String projectFinishDate(String projectId, String projectFinishDates, HttpSession session) throws ParseException {
        System.out.println("SetProjectFinishDate");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        if (projectFinishDates != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date projectFinishDate = df.parse(projectFinishDates);
            projectEntity.setProjectFinishDate(projectFinishDate);
        }


        Result result= projectManagementService.updateProjectSetProjectFinishDate(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框  修改合同经费
     *
     * @param projectId
     * @param projectFund
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectFund")
    public String projectLauchDate(String projectId, Integer projectFund, HttpSession session) {
        System.out.println("SetProjectFund");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectFund(projectFund);


        Result result= projectManagementService.updateProjectSetProjectFund(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }


    /**
     * 模态框  修改项目来源类别
     *
     * @param projectId
     * @param projectSourceType
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectSourceType")
    public String projectSourceType(String projectId, @RequestParam(value = "projectSourceType") Const.ProjectSourceType projectSourceType, HttpSession session) {
        System.out.println("SetProjectSourceType");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectSourceType(projectSourceType);


        Result result= projectManagementService.updateProjectSetProjectSourceType(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框  修改研究类别
     *
     * @param projectId
     * @param projectResearchType
     * @return 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetProjectResearchType")
    public String projectResearchType(String projectId, @RequestParam(value = "projectResearchType") Const.ProjectResearchType projectResearchType, HttpSession session) {
        System.out.println("SetProjectResearchType");

        UserEntity operator = (UserEntity) session.getAttribute("user");
        ProjectEntity projectEntity = new ProjectEntity(projectId);
        projectEntity.setProjectResearchType(projectResearchType);


        Result result= projectManagementService.updateProjectSetProjectResearchType(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
        return result.toString();
    }

    /**
     * 模态框  添加负责人时，根据 名称模糊查询 或者 userid 查询
     *
     * @param input
     * @param flag
     * @return 输入：flag 分辨是根据名称还是userid 查询
     * <p>
     * 输出：userEnity 列表，包括 userid 、username
     */
    @ResponseBody
    @RequestMapping("/FindUserByAll")
    public String FindUserByAll(String input, String flag, HttpSession session) {
        System.out.println("FindUserByAll");

        if (flag.equals("userName")) {

        } else if (flag.equals("userId")) {

        }


        JSONObject result = new JSONObject();
        result.put("message", "success");
        return result.toString();
    }

    @RequestMapping("/cwupload")
    @ResponseBody
    public Object insert(HttpServletRequest request, HttpServletResponse response,HttpSession session
            , @RequestParam("file") MultipartFile[] file) throws Exception {
        System.out.println("cwupload+++++++++++++++++++++++++++++++++++++++++++++++");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        Result result=null;
        if (file != null && file.length > 0) {
            //组合image名称，“;隔开”
            List<String> fileName = new ArrayList<String>();
            try {

                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        fileName.add(file[i].getOriginalFilename());
                       // String localPath = "/Users/zl/share/";
                       //  String localPath = "/templates/";
                        String localPath = request.getSession().getServletContext().getRealPath("/")+"upload/";
                    //在项目新建一个 你重新生成名称的文件
                        System.out.println(localPath);
                        String fileNameTemp = file[i].getOriginalFilename();

                        String pathname = localPath + fileNameTemp;


                        File dest = new File(pathname);
                        if (!dest.getParentFile().exists()) {
                            dest.getParentFile().mkdirs();
                        }
                        try {
                            file[i].transferTo(dest);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //上传文件，随机名称，";"分号隔开
                        // fileName.add(FileUtil.uploadImage(request, "/upload/"+TimeUtils.formateString(new Date(), "yyyy-MM-dd")+"/", file[i], true));
                    }
                }

                //上传成功
                if (fileName != null && fileName.size() > 0) {
                    System.out.println("上传成功！");
                    System.out.println(response.toString() + fileName);
                    String path = "http://localhost:8088/download/"+fileName;
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setFileStatus(Const.FileStatus.NORMAL);
                    fileEntity.setFilePath(path);
                    fileEntity.setProject(new ProjectEntity("001"));
                     result= projectManagementService.addFile(fileEntity,operator);

                } else {
                    System.out.println(response.toString() + "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(response.toString() + "上传出现异常！异常出现在：class.UploadController.insert()");
            }
        } else {
            System.out.println(response.toString() + "没有检测到文件！");
        }
        JSONObject object = new JSONObject();
        //  object.put("id", 123);
        return object;
    }

    @RequestMapping(value = "/SearchMember")
    public String SearchMember(Model model, String projectId, String userId, HttpSession session) {
        UserEntity operator = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = new UserEntity(userId);

      /*  userEntity.setUserId(userId);
        userEntity.setUserName("2323");
        userEntity.setPhone("22222PHONE");
        userEntity.setEmail("22222MAIL");
        userEntity.setInstitute(new InstituteEntity("12", "图书馆"));
*/
        Result result= projectManagementService.findAllUserByUserIdLike(userEntity,operator);

        //  Result result = new Result(true, "AddProject");

        model.addAttribute("userInfo", result.getObject("userEntityArrayList"));


        return "fragment::addMemberFragment";
    }

    /**
     * 获取成员列表
     * @param model
     * @param projectId
     * @param session
     * @return
     */
    @RequestMapping(value = "/InitMember")
    public String InitMember(Model model, String projectId, HttpSession session) {
        System.out.println("InitMember");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        List<UserEntity> lists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId("userId" + i);
            userEntity.setUserName("2323");
            userEntity.setPhone("22222PHONE");
            userEntity.setEmail("22222MAIL");
            userEntity.setInstitute(new InstituteEntity("12", "图书馆"));
            lists.add(userEntity);
        }

        model.addAttribute("lists", lists);
        return "fragment::MemberFragment";
    }

    @ResponseBody
    @RequestMapping(value = "/DeleteRow")
    public String InitMember( String projectId, HttpSession session,String userId) {
        System.out.println("DeleteRow");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        UserEntity userEntity = new UserEntity(userId);
        ProjectEntity projectEntity = new ProjectEntity(projectId);
         Set<UserEntity>members = new HashSet<>();
        members.add(userEntity);
        projectEntity.setMembers(members);
        Result result= projectManagementService.updateProjectMembers(projectEntity,operator);

        //  Result result = new Result(true, "AddProject");
   return result.toString();
    }


    /**
     * 模态框  导入 文件
     * 注：已经从excel中抽取出内容 但为未进行详细校验 和保存到数据库中，具体请看 excelOpt类 batchImport方法
     * @param projectFile
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @PostMapping("/ImportProjectExcel")
    public String  addProject( MultipartFile projectFile,HttpSession session) {
        System.out.println("ImportProjectExcel");
        Result result=null;
      //  UserEntity operator =(UserEntity) session.getAttribute("user");
        UserEntity operator = new UserEntity ("222");
        ExcelOpt excelOpt = new ExcelOpt();
        boolean a = false;
        String fileName = projectFile.getOriginalFilename();
        try {
            List<ProjectEntity> projectEntities = excelOpt.projectImport(fileName, projectFile);
            for(ProjectEntity temp:projectEntities){
                 result = projectManagementService.addProject(temp,operator);
                System.out.println(result);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result.toString());
    }



}
