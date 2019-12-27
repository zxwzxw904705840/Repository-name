package com.example.demo.controller;


import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.controller.admin.UserManagementController;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * excel 操作
 *
 */
@Component
public class ExcelOpt {

    @Autowired
    private ProjectManagementService projectManagementService;

    @Autowired
    private UserService userService;

    /**
     * 研究人员信息导入
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     *
     * ps:输入  title  phone email   可以为空
     *         password institute  userName  不可为空
     */

    public List<UserEntity> batchImport(String fileName, MultipartFile file ) throws Exception {

        UserEntity userEntity = new UserEntity();

        boolean notNull = false;

        List<UserEntity> userEntities = new ArrayList<>();

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        System.out.println("sheet.getLastRowNum()"+sheet.getLastRowNum()+"\n");
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                break;
            }



            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String userId = row.getCell(0).getStringCellValue();

            if(userId == null || userId.isEmpty()){
                break;
            }else{
                userEntity.setUserId(userId);


            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String userName = row.getCell(1).getStringCellValue();

            if(userName == null || userName.isEmpty()){
                break;
            }else{

                userEntity.setUserName(userName);

            }

            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(2).getStringCellValue();
            if(password==null || password.isEmpty()){
                break;
            }else{
                userEntity.setPassword(password);


            }


            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String phone = row.getCell(3).getStringCellValue();
            if(phone==null || phone.isEmpty()){
            }else{
                userEntity.setPhone(phone);


            }


            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String email = row.getCell(4).getStringCellValue();
            if(email==null || email.isEmpty()){
            }else{
                userEntity.setEmail(email);


            }

            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String instituteId = row.getCell(5).getStringCellValue();
            if(instituteId==null || instituteId.isEmpty()){
                break;
                //  throw new Exception("导入失败(第"+(r+1)+"行,name未填写)");
            }else{
                InstituteEntity instituteEntity = new InstituteEntity(instituteId);
                userEntity.setInstitute(instituteEntity);


            }

            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            String titles = row.getCell(6).getStringCellValue();
            titles = titles.replaceAll("\\s*", ""); //去除空格等


            if(titles!=null && !titles.isEmpty()){
                Const.UserTitle title =   Const.UserTitle.valueOf(titles);
                userEntity.setTitle(title);


            }

/*
System.out.println(userEntity.getUserId());
         //   System.out.println(userEntity.getCharacters());
            System.out.println(userEntity.getUserName());
            System.out.println(userEntity.getPassword());
       //     System.out.println(userEntity.getUserStatus());
            System.out.println(userEntity.getPhone());
            System.out.println(userEntity.getTitle());
            System.out.println(userEntity.getEmail());
            System.out.println(userEntity.getInstitute().getInstituteId());*/

/*
            userEntity.setUserName("111");
            userEntity.setPassword("12121213");
            userEntity.setInstitute(new InstituteEntity("1"));
            userEntity.setEmail("23117877777@qq.com");
            userEntity.setPhone("15983408240");
            userEntity.setTitle(Const.UserTitle.PROFESSOR);
            userEntity.setUserStatus(Const.UserStatus.NORMAL);
            userEntity.setCharacters(Const.UserCharacter.TEACHER);*/

            userEntities.add(userEntity);
        }

        return userEntities;
    }


    /**
     * 项目信息导入
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     *
     * ps:输入  title  phone email   可以为空
     *         password institute  userName  不可为空
     */


    public List<ProjectEntity> projectImport(String fileName, MultipartFile file) throws Exception {

        ProjectEntity projectEntity = new ProjectEntity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        List<ProjectEntity> projectEntities =new ArrayList<>();

        boolean notNull = false;

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }


        System.out.println("sheet.getLastRowNum()"+sheet.getLastRowNum()+"\n");
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                break;
            }


            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String projectId = row.getCell(0).getStringCellValue();

            if(projectId == null || projectId.isEmpty()){
                break;
            }else{
                projectEntity.setProjectId(projectId);

            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String projectCode = row.getCell(1).getStringCellValue();
            if(projectCode==null || projectCode.isEmpty()){
                break;
            }else{
                projectEntity.setProjectCode(projectCode);

            }


            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String projectName = row.getCell(2).getStringCellValue();
            if(projectName==null || projectName.isEmpty()){
            }else{
                projectEntity.setProjectName(projectName);

            }


            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String projectTypes = row.getCell(3).getStringCellValue();

            if(projectTypes==null || projectTypes.isEmpty()){
                break;
            }else{
                Const.ProjectType projectType =   Const.ProjectType.valueOf(projectTypes);
                projectEntity.setProjectType(projectType);

            }

            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String userId = row.getCell(4).getStringCellValue();

            if(userId==null || userId.isEmpty()){
                break;
            }else{
                UserEntity userEntity = new UserEntity(userId);
                projectEntity.setProjectManager(userEntity);

            }

            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String projectLevels = row.getCell(5).getStringCellValue();

            if(projectLevels==null || projectLevels.isEmpty()){
                break;
            }else{
                Const.ProjectLevel projectLevel =   Const.ProjectLevel.valueOf(projectLevels);
                projectEntity.setProjectLevel(projectLevel);

            }
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            String projectProcesss = row.getCell(6).getStringCellValue();

            if(projectProcesss==null || projectProcesss.isEmpty()){
                break;
            }else{
                Const.ProjectProgress projectProcess =   Const.ProjectProgress.valueOf(projectProcesss);
                projectEntity.setProjectProcess(projectProcess);

            }
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            String projectSource = row.getCell(7).getStringCellValue();

            if(projectSource==null || projectSource.isEmpty()){
                break;
            }else{
                projectEntity.setProjectSource(projectSource);

            }


            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            String projectEstablishDate = row.getCell(8).getStringCellValue();

            if(projectEstablishDate==null || projectEstablishDate.isEmpty()){
                break;
            }else{
                Date time = sdf.parse(projectEstablishDate);
                projectEntity.setProjectEstablishDate(time);

            }

            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            String projectPlannedDate = row.getCell(9).getStringCellValue();

            if(projectPlannedDate==null || projectPlannedDate.isEmpty()){
                break;
            }else{
                Date time = sdf.parse(projectPlannedDate);
                projectEntity.setProjectPlannedDate(time);

            }
            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
            String projectLaunchDate = row.getCell(10).getStringCellValue();

            if(projectLaunchDate==null || projectLaunchDate.isEmpty()){
                break;
            }else{
                Date time = sdf.parse(projectLaunchDate);
                projectEntity.setProjectLaunchDate(time);

            }

            row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
            String projectFinishDate = row.getCell(11).getStringCellValue();

            if(projectFinishDate==null || projectFinishDate.isEmpty()){
                break;
            }else{
                Date time = sdf.parse(projectFinishDate);
                projectEntity.setProjectFinishDate(time);

            }

            row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
            String projectFunds = row.getCell(12).getStringCellValue();

            if(projectFunds==null || projectFunds.isEmpty()){
                break;
            }else{

                projectEntity.setProjectFund(Integer.valueOf(projectFunds));

            }
            row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
            String projectSourceTypes = row.getCell(13).getStringCellValue();

            if(projectSourceTypes==null || projectSourceTypes.isEmpty()){
                break;
            }else{
                Const.ProjectSourceType projectSourceType =   Const.ProjectSourceType.valueOf(projectSourceTypes);
                projectEntity.setProjectSourceType(projectSourceType);

            }

            row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
            String projectResearchTypes = row.getCell(14).getStringCellValue();

            if(projectResearchTypes==null || projectResearchTypes.isEmpty()){
                break;
            }else{
                Const.ProjectResearchType projectResearchType =   Const.ProjectResearchType.valueOf(projectResearchTypes);
                projectEntity.setProjectResearchType(projectResearchType);

            }
          //  Result result = projectManagementService.addProject(projectEntity,operator);

            projectEntities.add(projectEntity);
           /*
            if(result.toString().split(" ")[0].equals("true")){

            }else {
                return
            }*/


        }

        return projectEntities;
    }



}

