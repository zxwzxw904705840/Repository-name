package com.example.demo;

import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Test
    void timeTest() throws ParseException {
          Date time = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeStr = "2019-12-25";
            time = sdf.parse(timeStr);
            System.out.println(time);
        System.out.println(timeStr);

    }

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
