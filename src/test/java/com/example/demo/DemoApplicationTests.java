package com.example.demo;

<<<<<<< HEAD
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
=======
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> fb04febe7a717a25df91fa3327380ae78655c114
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserServiceImpl us;

    @Test
    void TestResult(){
        Result result = new Result(true,"message");
<<<<<<< HEAD
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("123");

        result.addObject("user",userEntity);


        System.out.println(result.isSuccess());
        System.out.println(result.getMessage());
        UserEntity userEntity1 = (UserEntity) result.getObject("user");
        System.out.println(userEntity1.getUserId());
        //或者
        System.out.println(((UserEntity) result.getObject("user")).getUserId());
=======

        //region User, Admin测试数据
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("123");
        userEntity.setUserName("demo");
        userEntity.setPassword("1231231");
        userEntity.setPhone("12345678911");
        userEntity.setEmail("anamsajfsad");
        userEntity.setTitle(Const.UserTitle.LECTURER);
        userEntity.setCharacters(Const.UserCharacter.NORMAL_USER);
        userEntity.setUserStatus(Const.UserStatus.NORMAL);

        UserEntity adminEntity = new UserEntity();
        adminEntity.setUserId("111");
        adminEntity.setUserName("demo");
        adminEntity.setPassword("1231231");
        adminEntity.setPhone("12345678911");
        adminEntity.setEmail("anamsajfsad");
        adminEntity.setTitle(Const.UserTitle.LECTURER);
        adminEntity.setCharacters(Const.UserCharacter.ADMINISTRATION);
        adminEntity.setUserStatus(Const.UserStatus.NORMAL);
//
//        us.addUser(userEntity);
//        us.addUser(adminEntity);
        //endregion

        //region UserServiceImpl里的getUserById测试
//        String userId = "123";
//        //us.addUser(adminEntity);
//        System.out.println(us.getUserById("123",adminEntity));
//        Result id = us.getUserById("123",adminEntity);
//        UserEntity u = (UserEntity) id.getObject(userId);
//        System.out.println(u.getUserId());
        //endregion

        //region zxw测试块
//        result.addObject("user",userEntity);
//        System.out.println(result.isSuccess());
//        System.out.println(result.getMessage());
//        UserEntity userEntity1 = (UserEntity) result.getObject("user");
//        System.out.println(userEntity1.getUserId());
//        //或者
//        System.out.println(((UserEntity) result.getObject("user")).getUserId());
//        System.out.println(((UserEntity) result.getObject("user")).getUserName());
        //endregion
>>>>>>> fb04febe7a717a25df91fa3327380ae78655c114
    }
    @Test
    void contextLoads() {
    }

}
