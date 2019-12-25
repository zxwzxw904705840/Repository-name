package com.example.demo.controller.admin;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexManagementController {

    @GetMapping("/indexM")
    public  String indexM(HttpSession session){
        //测试用
        UserEntity userInfo = new UserEntity();
        userInfo.setUserName("李莉");
        userInfo.setUserId("1001");
        session.setAttribute("user",userInfo);

        return "indexM";
    }

    @GetMapping("/userManagement")
    public  String indexUM(Model model,HttpSession session){
        UserEntity operator =(UserEntity) session.getAttribute("user");
        //调用接口

        List<InstituteEntity> lists = new ArrayList<>();
        for(int i=0;i<5;i++){
            InstituteEntity temp = new InstituteEntity();
            temp.setInstituteId("id"+i);
            temp.setInstituteName("name"+i);
            lists.add(temp);
        }
        model.addAttribute("lists",lists);
        return "userManagement";
    }

    @GetMapping("/projectManagement")
    public  String indexPM(Model model,HttpSession session){
        UserEntity operator =(UserEntity) session.getAttribute("user");
        //调用接口

        List<InstituteEntity> lists = new ArrayList<>();
        for(int i=0;i<5;i++){
            InstituteEntity temp = new InstituteEntity();
            temp.setInstituteId("id"+i);
            temp.setInstituteName("name"+i);
            lists.add(temp);
        }
        model.addAttribute("lists",lists);

        return "projectManagement";
    }

    @GetMapping("/bookManagement")
    public  String indexBM(){

        return "bookManagement";
    }

    @GetMapping("/thesisManagement")
    public  String indexTM(){

        return "thesisManagement";
    }
}
