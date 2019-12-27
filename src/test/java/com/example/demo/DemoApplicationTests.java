package com.example.demo;

import com.example.demo.Entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void TestResult(){
        Result result = new Result(true,"message");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("123");

        result.addObject("user",userEntity);


        System.out.println(result.isSuccess());
        System.out.println(result.getMessage());
        UserEntity userEntity1 = (UserEntity) result.getObject("user");
        System.out.println(userEntity1.getUserId());
        //或者
        System.out.println(((UserEntity) result.getObject("user")).getUserId());
    }
    @Test
    void contextLoads() {



    }

}
