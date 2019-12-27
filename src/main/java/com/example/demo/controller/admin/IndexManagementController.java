package com.example.demo.controller.admin;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexManagementController {

    @Autowired
    private ProjectManagementService projectManagementService;

    @GetMapping("test")
    public String test(Model model) {
        Result result =projectManagementService.findAllInstitute();
        model.addAttribute("lists",result.getObject("lists"));

        return "test";
    }

    @GetMapping("/indexM")
    public  String indexM(HttpSession session){
        //测试用
        UserEntity userInfo = new UserEntity();
        userInfo.setUserName("李莉");
        userInfo.setUserId("222");
        session.setAttribute("user",userInfo);

        return "indexM";
    }

    @GetMapping("/userManagement")
    public  String indexUM(Model model,HttpSession session){
        UserEntity operator =(UserEntity) session.getAttribute("user");
        //调用接口

       /* List<InstituteEntity> lists = new ArrayList<>();
        for(int i=0;i<5;i++){
            InstituteEntity temp = new InstituteEntity();
            temp.setInstituteId("id"+i);
            temp.setInstituteName("name"+i);
            lists.add(temp);
        }*/
        Result result =projectManagementService.findAllInstitute();
        model.addAttribute("lists",result.getObject("lists"));


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
