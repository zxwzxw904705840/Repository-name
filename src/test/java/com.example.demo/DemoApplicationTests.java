package com.example.demo;

import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration
@Configuration
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    UserServiceImpl userService;

    @Test
    public void TestAddThesis(){
        System.out.println("asd");
//        Result result = userService.getUserById("123");
//        UserEntity userEntity = (UserEntity) result.getObject("123");
//        ThesisEntity thesisEntity = new ThesisEntity();
//        thesisEntity.setThesisId("123");
//        thesisEntity.setJournal("journal");
//        thesisEntity.setPages(100);
//        thesisEntity.setPrivacy(Const.ThesisPrivacy.PUBLIC);
//        thesisEntity.setStatus(Const.ThesisStatus.NORMAL);
//        thesisEntity.setThesisTitle("ABCDEMOTITLE");
//        thesisEntity.setVolume("asd");
//        thesisEntity.setAuthor1(userEntity);
//        userService.addThesis(thesisEntity,userEntity);
    }
}
