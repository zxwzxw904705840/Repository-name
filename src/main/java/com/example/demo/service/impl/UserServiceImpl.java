package com.example.demo.service.impl;

import com.example.demo.Entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Const;
import com.example.demo.utils.DataCheck;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

//    private Result getUserList(){}
//    Result getUserById(String userId);
//    Result addUser(UserEntity user);
//    Result addUserExcel(UserEntity user);
//    Result AgreeUserUpdate(UserEntity user, UserEntity admin);
//    Result SetUserName(String userId,String userName);
//    Result SetInstituteId(String userId,String instituteId);
//    Result SetTitle(String userId, Const.UserTitle title);
//    Result SetEmail(String userId,String email);
//    Result SetPassword(String userId,String password);
//    Result SetPhone(String userId,String phone);
//    Result UserExcelExport();

    public Result addUser(UserEntity user){
        UserEntity newUser = new UserEntity();
        Result result = checkUser(user);

        if(result.getMessage().contentEquals(DataCheck.UserDataCheck.USERID_NOT_EXISTS.toString())){
            userRepository.save(user);
            System.out.println(result.getMessage());
            return new Result(true, "新用户添加了！");
        }
        System.out.println(result.getMessage());
        return new Result(false, "添加新用户出问题，　用户存在！");
    }

    public Result getUserById(String userId, UserEntity admin){
        Result result = checkUserPermission(admin);
        if(!result.isSuccess()){
            return result;
        }
        UserEntity userEntity = userRepository.findByUserId(userId);
        result.addObject(userId,userEntity);
        return result;
    }



    private Result checkUserPermission(UserEntity user){
        if(user==null||user.getUserId()==null||user.getUserId().equals("")) {
            return new Result(false, DataCheck.UserDataCheck.PERMISSION_DENIED.toString());
        }

        UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        if(userEntity==null||userEntity.getCharacters()!= Const.UserCharacter.ADMINISTRATION){
            return new Result(false, DataCheck.UserDataCheck.PERMISSION_DENIED.toString());
        }
        return new Result(true, DataCheck.UserDataCheck.ACCOUNT_CAN_USE.toString());
    }
    private Result checkUser(UserEntity user){
        if (user==null){
            return new Result(false, DataCheck.UserDataCheck.USERID_NOT_EXISTS.toString());
        }
        if(user.getUserId()==null||user.getUserId().equals("")){
            return new Result(false, DataCheck.UserDataCheck.USERID_IS_EMPTY.toString());
        }
        if(user.getUserId().length()>10){
            return new Result(false, DataCheck.UserDataCheck.USERID_IS_TOO_LONG.toString());
        }
        if(user.getPassword()==null||user.getPassword().equals("")){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_IS_EMPTY.toString());
        }
        if(user.getPassword().length()<6){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_TOO_SHORT.toString());
        }
        if(user.getPassword().length()>16){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_TOO_LONG.toString());
        }
        if(user.getCharacters()!= Const.UserCharacter.TEACHER && user.getCharacters()!=Const.UserCharacter.ADMINISTRATION &&
           user.getCharacters()!= Const.UserCharacter.NORMAL_USER) {
            return new Result(false, DataCheck.UserDataCheck.USER_ROLE_ERROR.toString());
        }
        if(user.getUserName()==null||user.getUserName().equals("")){
            return new Result(false, DataCheck.UserDataCheck.USERNAME_IS_EMPTY.toString());
        }
        if(user.getPhone()==null||user.getPhone().equals("")){
            return new Result(false, DataCheck.UserDataCheck.PHONE_IS_EMPTY.toString());
        }
        if(user.getPhone().length()!=11){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
        try{
            System.out.println(Integer.parseInt("123456789"));
            //Integer.parseInt(user.getPhone());
        }catch (Exception e){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
        UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        if(userEntity==null || userEntity.getUserStatus()!= Const.UserStatus.NORMAL){
            return new Result(false, DataCheck.UserDataCheck.USERID_NOT_EXISTS.toString());
        }
        if(user.getTitle()==null){
            return new Result(false, DataCheck.UserDataCheck.EMPTY_TITLE.toString());
        }
        return new Result(true, DataCheck.UserDataCheck.ACCOUNT_CAN_USE.toString());
    }

}
