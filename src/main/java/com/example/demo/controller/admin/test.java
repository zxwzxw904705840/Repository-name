package com.example.demo.controller.admin;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.apache.catalina.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class test {


    @Autowired
    private UserService userService;
    @Autowired
    private ProjectManagementService projectManagementService;


    @ResponseBody
    @RequestMapping("/search")
    public String search(HttpServletRequest request){
        System.out.println("search");
        System.out.println(request.getParameter("date"));
        String [] institutes = request.getParameterValues("institute");
        for(String tmp:institutes){
            System.out.println(tmp);
        }

        Result result=null;

        return  result.toString();
    }

}
