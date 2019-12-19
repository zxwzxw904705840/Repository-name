package com.example.demo.controller;

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

    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        Map<String,Object> param = new HashMap<>();

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
                param.put("userName",userName);
            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(1).getStringCellValue();
            if(password==null || password.isEmpty()){
                break;
            }else{
                param.put("password",password);
            }


            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String phone = row.getCell(2).getStringCellValue();
            if(phone==null || phone.isEmpty()){
            }else{
                param.put("phone",phone);
            }


            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String email = row.getCell(3).getStringCellValue();
            if(email==null || email.isEmpty()){
            }else{
                param.put("email",email);
            }

            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String instituteId = row.getCell(4).getStringCellValue();
            if(instituteId==null || instituteId.isEmpty()){
                break;
                //  throw new Exception("导入失败(第"+(r+1)+"行,name未填写)");
            }else{
                param.put("instituteId",instituteId);
            }

            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String titles = row.getCell(5).getStringCellValue();
            titles = titles.replaceAll("\\s*", ""); //去除空格等

            Integer title = null;

            if(titles!=null && !titles.isEmpty()){
                title = Integer.valueOf(titles);
                param.put("title",title);

            }



            /**
             * 根据Map<String,Object> param 导入数据库
             * 其中  title  phone email   如果为空 则不存在
             *      password institute  userName  不可为空比存在
             *
             */
            System.out.println(param);
        }

        return notNull;
    }




}

