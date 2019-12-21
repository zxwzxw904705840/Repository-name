package com.example.demo.service;

import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;

public interface UserService {
    Result getUserList();
    Result getUserById(String userId, UserEntity admin);
    Result addUser(UserEntity user);
    Result addUserExcel(UserEntity user);
    Result AgreeUserUpdate(UserEntity user, UserEntity admin);
    Result SetUserName(String userId,String userName);
    Result SetInstituteId(String userId,String instituteId);
    Result SetTitle(String userId, Const.UserTitle title);
    Result SetEmail(String userId,String email);
    Result SetPassword(String userId,String password);
    Result SetPhone(String userId,String phone);
    Result UserExcelExport();
}
