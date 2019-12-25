package com.example.demo.controller;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * excel 操作
 *
 */
public class ExcelOpt {

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

    public boolean batchImport(String fileName, MultipartFile file,UserEntity operator) throws Exception {

        UserEntity userEntity = new UserEntity();

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
            String userName = row.getCell(0).getStringCellValue();

            if(userName == null || userName.isEmpty()){
                break;
            }else{
                userEntity.setUserName(userName);

            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(1).getStringCellValue();
            if(password==null || password.isEmpty()){
                break;
            }else{
                userEntity.setPassword(password);

            }


            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String phone = row.getCell(2).getStringCellValue();
            if(phone==null || phone.isEmpty()){
            }else{
                userEntity.setPhone(phone);

            }


            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String email = row.getCell(3).getStringCellValue();
            if(email==null || email.isEmpty()){
            }else{
                userEntity.setEmail(email);

            }

            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String instituteId = row.getCell(4).getStringCellValue();
            if(instituteId==null || instituteId.isEmpty()){
                break;
                //  throw new Exception("导入失败(第"+(r+1)+"行,name未填写)");
            }else{
                InstituteEntity instituteEntity = new InstituteEntity(instituteId);
                userEntity.setInstitute(instituteEntity);

            }

            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String titles = row.getCell(5).getStringCellValue();
            titles = titles.replaceAll("\\s*", ""); //去除空格等


            if(titles!=null && !titles.isEmpty()){
                Const.UserTitle title =   Const.UserTitle.valueOf(titles);
                userEntity.setTitle(title);

            }

            //调用service
        }

        return notNull;
    }




}

