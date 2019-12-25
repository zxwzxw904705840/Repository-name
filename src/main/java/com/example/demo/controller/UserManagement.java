package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;

import com.example.demo.Entity.UserEntity;
import com.example.demo.service.impl.UserServiceImpl;
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


@Controller
public class UserManagement {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/userManagement")
    public  String index(){

        return "userManagement";
    }

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
    public Map<String, Object> findTBlist(HttpServletRequest request) {
        System.out.println("FindUserList");


        int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        int limit = Integer.valueOf(request.getParameter("pageSize")); //输入
        int offset = limit * (pageNumber - 1);//输入
        String userStatus = request.getParameter("userStatus");//输入
        String userName = request.getParameter("userName");//输入
        String instituteId = request.getParameter("instituteId");//输入


        Map<String, Object> params = new HashMap<>();
        params.put("userStatus", userStatus);
        params.put("userName", userName);
        params.put("instituteId", instituteId);
        params.put("limit", limit);
        params.put("offset", offset);
        System.out.println("params" + params);

/*
        //测试用数据
       List<userList> lists = new ArrayList<>();
        for(int i =offset;i<offset+10;i++){
            userList temp = new userList();
            temp.setUserId("userid"+i);
            temp.setUserName("李莉"+i);
            temp.setPassword("1000"+i);
            temp.setPhone("phone"+i);
            temp.setEmail("email"+i);
            temp.setTitle(i%5);
            temp.setInsituteName("学院"+i);
            temp.setUserStatus(i%2);
            lists.add(temp);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("rows", lists);
        result.put("total",100); //整个表的总数*/

        //测试用数据end

//        //List<UserEntity> userlist = userServiceImpl.findByUsernameContainingOrInstituteContainingOrUserStatusContaining(userName,instituteId,userStatus);
        Map<String, Object> result = new HashMap<>();
//        result.put("rows", userlist);
//        result.put("total", userlist.size());

        return result;

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

        Result resultService = userServiceImpl.DeleteUsersById(ids);

        JSONObject results =new JSONObject();
        results.put(resultService.getMessage(),resultService.isSuccess());

        return results.toString();
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
    public String AddUser(@RequestBody JSONObject userInfos ) {
        System.out.println("AddUser");
      //  System.out.println(userInfos);
        UserEntity user = JSONObject.toJavaObject(userInfos,UserEntity.class);

        Result resultService = userServiceImpl.addUser(user);

        JSONObject result=new JSONObject();
        result.put(resultService.getMessage(),resultService.isSuccess());
        return result.toString();
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
    public String AgreeUserUpdate(String userId, String adminid) {
        System.out.println("AgreeUserUpdate");

        Result resultAgree = userServiceImpl.AgreeUserUpdate(userId, adminid);

        JSONObject result = new JSONObject();
        result.put(resultAgree.getMessage(),resultAgree.isSuccess());
        return result.toString();
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
    public boolean addUser( MultipartFile userFile) {
        System.out.println("ImportUserExcel");
        ExcelOpt excelOpt = new ExcelOpt();
        boolean a = false;
        String fileName = userFile.getOriginalFilename();
        try {
            a = excelOpt.batchImport(fileName, userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
        return  a;
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

        Result resultSetName = userServiceImpl.SetUserName(userId, userName);

        JSONObject result=new JSONObject();
        result.put(resultSetName.getMessage(),resultSetName.isSuccess());
        return result.toString();
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

        Result resultSetInst = userServiceImpl.SetInstituteId(userId,instituteId);

        JSONObject result=new JSONObject();
        result.put(resultSetInst.getMessage(),resultSetInst.isSuccess());
        return result.toString();
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
    public String SetTitle(String userId,@RequestParam(value="title") Const.UserTitle title) {
        System.out.println("SetTitle");

        Result resultTitle = userServiceImpl.SetTitle(userId, title);

        JSONObject result=new JSONObject();
        result.put(resultTitle.getMessage(),resultTitle.isSuccess());
        return result.toString();
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

        Result resultEmail = userServiceImpl.SetEmail(userId, email);

        JSONObject result=new JSONObject();
        result.put(resultEmail.getMessage(),resultEmail.isSuccess());
        return result.toString();
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

        Result resultPass = userServiceImpl.SetPassword(userId,password);

        JSONObject result=new JSONObject();
        result.put(resultPass.getMessage(),resultPass.isSuccess());
        return result.toString();
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

        Result resultPhone = userServiceImpl.SetPhone(userId, phone);

        JSONObject result=new JSONObject();
        result.put(resultPhone.getMessage(),resultPhone.isSuccess());
        return result.toString();
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

        /*  List<userEnity> lists = (List)session.getAttribute("purchaselist");*/

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

        /*   String[][] content = new String[lists.size()][title.length];*/
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
       /*
        for (int i = 0; i < lists.size(); i++) {
            content[i] = new String[title.length];
            userEnity obj = lists.get(i);
            content[i][0] = obj.getIsbn();
            content[i][1] = obj.getBookname();
            content[i][2] = obj.getApplynum().toString();
            content[i][3] = obj.getAgreeDate();


        }


        for(int i=0;i<content.length;i++){

            row = sheet.createRow(i + 1);

            for(int j=0;j<content[i].length;j++){

                //将内容按顺序赋给对应的列对象
                HSSFCell cel = row.createCell(j);
                cel.setCellValue(content[i][j]);

            } }*/


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
