package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSONObject;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.controller.ExcelOpt;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 管理员——用户管理
 *
 */
@Controller
public class UserManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectManagementService projectManagementService;



    /**
     * 页面 表格
     * @param request
     * @return
     *
     * 输入：limit(int)，offset(int),
     *      userStatus(Integer)
     *      instituteId(String)
     *      userName(String)
     *      ps: 后面三个可能为空
     *           支持用户名模糊查询
     * 返回：rows 列表
     *      total 总数
     */
    @ResponseBody
    @RequestMapping("/FindUserList")
    public  List<UserEntity> findTBlist(HttpServletRequest request,HttpSession session) {
        System.out.println("FindUserList");

        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize"));
        int offset = limit * (pageNumber - 1);
        Const.UserStatus  userStatus = null;
        String userName = request.getParameter("userName");// 可能为空
        String userStatusTemp = request.getParameter("userStatus").toString();
        String instituteId = request.getParameter("instituteId"); //ALL
        InstituteEntity instituteEntity=null;
        int choos1or2=2;
        if(!userStatusTemp.equals("ALL")&&!instituteId.equals("ALL")){
            userStatus = Const.UserStatus.valueOf(userStatusTemp);
            instituteEntity=new InstituteEntity(instituteId);
            choos1or2=1;
        }



        List<UserEntity> lists=new ArrayList<>();
        if(choos1or2==2){
            lists = userService.findByserStatusNot(Const.UserStatus.DELETED);
        }else if(choos1or2==1){
            lists=userService.getUserList(userName,userStatus,instituteEntity);
        }


        session.setAttribute("downloadsUser",lists);
        return lists;

    }

    /**
     * 页面  批量删除
     *
     * @param ids
     * @return
     *
     * 输入： userid []
     *
     * 返回： 成功/失败
     */
    @ResponseBody
    @RequestMapping("/RemoveUser")
    public String RemoveUser(String[] ids) {
        System.out.println("RemoveUser");
        Result   result=null;
        try{
               result= userService.DeleteUsersById(ids);
        }catch (Exception e){
            e.printStackTrace();
        }


        return  result.toString();
    }

    /**
     * 模态框  新增科研人员-提交
     * @param userInfos
     * @return
     *
     * 输入：userInfos
     *      userInfos 包括 userName,password,phone,email,institudeId,title
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/AddUser")
    public String AddUser(@RequestBody JSONObject userInfos ,HttpSession session) {
        System.out.println("AddUser");

        UserEntity userEntity = new UserEntity();
        UserEntity operator = (UserEntity) session.getAttribute("user");
        try{
            userEntity.setUserId(userInfos.get("userId").toString());
            userEntity.setUserName(userInfos.get("userName").toString());
            userEntity.setPassword(userInfos.get("password").toString());
            if(!userInfos.get("phone").toString().equals("")){
                userEntity.setPhone(userInfos.get("phone").toString()); //可以为空
            }else{
                userEntity.setPhone(null); //可以为空
            }
            if(!userInfos.get("email").toString().equals("")){
                userEntity.setEmail(userInfos.get("email").toString()); //可以为空
            }else{
                userEntity.setEmail(null); //可以为空
            }
            InstituteEntity instituteEntity = new InstituteEntity(userInfos.get("instituteId").toString());
            userEntity.setInstitute(instituteEntity);

            if(userInfos.size()==7){
                userEntity.setTitle(Const.UserTitle.valueOf(userInfos.get("title").toString())); //可以为空
            }else {
                userEntity.setTitle(null);
            }
            userEntity.setCharacters(Const.UserCharacter.TEACHER);
        }catch (Exception e){
            e.printStackTrace();
        }

        //调用sevice
        Result result= userService.addUser(userEntity);

        return  result.toString();
    }

    /**
     *
     * 表单内 通过升级审核
     * @param userId
     * @return
     *
     * 输入：userId
     * 输出：成功/失败
     */

    @ResponseBody
    @RequestMapping("/AgreeUserUpdate")
    public String AgreeUserUpdate(String userId,HttpSession session) {
        System.out.println("AgreeUserUpdate");
        UserEntity operator = (UserEntity) session.getAttribute("user");
        //调用sevice

        Result result= userService.SetStatus(userId,operator,Const.UserStatus.NORMAL);

        return  result.toString();
    }

    /**
     * 模态框  导入 文件
     * 注：已经从excel中抽取出内容 但为未进行详细校验 和保存到数据库中，具体请看 excelOpt类 batchImport方法
     * @param userFile
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @PostMapping("/ImportUserExcel")
    public String  addUser( MultipartFile userFile,HttpSession session) {
        System.out.println("ImportUserExcel");
        UserEntity operator =(UserEntity) session.getAttribute("user");
        ExcelOpt excelOpt = new ExcelOpt();
        boolean a = false;
        Result result=null;
        String fileName = userFile.getOriginalFilename();
        try {
            List<UserEntity> userEntities = excelOpt.batchImport(fileName, userFile);
            for(UserEntity temp:userEntities){
                temp.setCharacters(Const.UserCharacter.TEACHER);
                System.out.println(temp.getUserName());
                 result = userService.addUser(temp);
                System.out.println(result);
            }

            System.out.println(String.valueOf(a));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(result.toString());
    }



    /**
     * 模态框 修改研究人员名称
     * @param userId
     * @param userName
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetUserName")
    public String SetUserName(String userId,String userName) {
        System.out.println("SetUserName");

        System.out.println(userId+":::"+userName);


        Result result= userService.SetUserName( userId, userName);

        return  result.toString();
    }
    /**
     * 模态框 修改研究人员所属学院
     * @param userId
     * @param instituteId
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetInstituteId")
    public String SetInstituteId(String userId,String instituteId) {
        System.out.println("titles");

        System.out.println(userId+":::"+instituteId);


        Result result= userService.SetInstituteId( userId, instituteId);

        return  result.toString();
    }

    /**
     * 模态框 修改研究人员职称
     * @param userId
     * @param title
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetTitle")
    public String SetTitle(String userId,String title) {
        System.out.println("SetTitle");
        System.out.println(userId+":::"+title);
        Const.UserTitle titles= null;
        if(!title.equals("STUDENT")){
            titles =  Const.UserTitle.valueOf(title);
        }

        Result result= userService.SetTitle( userId, titles);
        return  result.toString();
    }
    /**
     * 模态框 修改研究人员的电子邮箱
     * @param userId
     * @param email
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetEmail")
    public String SetEmail(String userId,String email) {
        System.out.println("SetEmail");

        System.out.println(userId+":::"+email);

        Result result= userService.SetEmail( userId, email);
        return  result.toString();
    }
    /**
     * 模态框 修改研究人员的密码
     * @param userId
     * @param password
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetPassword")
    public String SetPassword(String userId,String password) {
        System.out.println("SetPassword");

        System.out.println(userId+":::"+password);

        Result result= userService.SetPassword( userId, password);
        return  result.toString();
    }
    /**
     * 模态框 修改研究人员的联系方式
     * @param userId
     * @param phone
     * @return
     *
     * 输出：成功/失败
     */
    @ResponseBody
    @RequestMapping("/SetPhone")
    public String SetPhone(String userId,String phone) {
        System.out.println("SetPhone");

        System.out.println(userId+":::"+phone);

        Result result= userService.SetPhone( userId, phone);
        return  result.toString();
    }


    /**
     * 页面  研究人员信息导出 - 待讨论
     * @param request
     * @param response
     * @param session
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/userExcelExport")
    public void excelExportPurchase(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        System.out.println("userExcelExport");

          List<UserEntity> lists = (List)session.getAttribute("downloadsUser");
       // System.out.println(lists.size());
        //EXCEL表导出核心代码
        //   声明一个Excel
        HSSFWorkbook wb=null;

        //title代表的是你的excel表开头每列的名字
        String[] title =new String[]{"用户名","邮箱","手机号","职位","所属学院","用户状态"};
        Date current=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(current);
        String name = date.split(" ")[0];

        //excel文件名
        String fileName = name+"用户表"+".xls";

        //sheet名
        String sheetName = name+"用户表";

        //二维数组铺满整个Excel

           String[][] content = new String[lists.size()][title.length];
        //--------------------------------------------------------------------------------------------

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);


        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //设置背景色
        style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置居右
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平居右
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        //设置字体
        HSSFFont font=wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);//设置字体大小
        HSSFFont font2=wb.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 12);

        style.setFont(font);//选择需要用到的字体格式

        //设置列宽
        sheet.setColumnWidth(0, 4567);//第一个参数代表列id（从0开始），第二个参数代表宽度值
        sheet.setColumnWidth(1, 4567);//第一个参数代表列id（从1开始），第二个参数代表宽度值
        sheet.setColumnWidth(2, 4567);//第一个参数代表列id（从2开始），第二个参数代表宽度值
        sheet.setColumnWidth(3, 4567);//第一个参数代表列id（从3开始），第二个参数代表宽度值
        sheet.setColumnWidth(4, 4567);//第一个参数代表列id（从2开始），第二个参数代表宽度值
        sheet.setColumnWidth(5, 4567);//第一个参数代表列id（从3开始），第二个参数代表宽度值

        style.setWrapText(true);//设置自动换行

        //加边框
        HSSFCellStyle cellStyle=wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }


        /**
         * 填充内容
         */
try {

    for (int i = 0; i < lists.size(); i++) {
        content[i] = new String[title.length];
        UserEntity obj = lists.get(i);
        content[i][0] = obj.getUserName();
        content[i][1] = obj.getEmail();
        content[i][2] = obj.getPhone().toString();
        if(obj.getTitle()!=null){
            content[i][3] = obj.getTitle().toString();
        }else{
            content[i][3] = "";
        }
        if(obj.getInstitute()!=null){
            content[i][4] = obj.getInstitute().getInstituteName();
        }else{
            content[i][4] = "";
        }


        content[i][5] = obj.getUserStatus().toString();

    }
}catch (Exception e){
    e.printStackTrace();
}

        System.out.println(1);
        for(int i=0;i<content.length;i++){

            row = sheet.createRow(i + 1);

            for(int j=0;j<content[i].length;j++){

                //将内容按顺序赋给对应的列对象
                HSSFCell cel = row.createCell(j);
                cel.setCellValue(content[i][j]);

            } }


        //响应到客户端
        try {
            try {
                try {
                    fileName = new String(fileName.getBytes(),"ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                response.setContentType("application/octet-stream;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
