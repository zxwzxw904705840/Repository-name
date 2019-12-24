package com.example.demo.service;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;

import java.util.List;

public interface UserService {
    List<UserEntity> getUserList();
    Result getUserById(String userId);
    List<UserEntity> getUsersById(List<String> userId);
    List<UserEntity> getUsersByStatus(Const.UserStatus status, String adminId);
    List<UserEntity> getUsersByRole(Const.UserCharacter role);

    Result addUser(UserEntity user);
    Result addUsers(List<UserEntity> users);

    Result DeleteUserById(String userId);
    Result DeleteUsersById(String[] userId);
    Result DeleteCompletely(String userId, UserEntity admin);
    Result DeleteUsersCompletely(List<UserEntity> users, UserEntity personCanDelete);

    Result SetUserName(String userId,String userName);
    Result SetInstituteId(String userId,String instituteId);
    Result SetTitle(String userId, Const.UserTitle title);
    Result SetEmail(String userId,String email);
    Result SetPassword(String userId,String password);
    Result SetPhone(String userId,String phone);
    Result SetStatus(String userId, UserEntity admin, Const.UserStatus status);

    Result checkInstitute(InstituteEntity institute);
    Result checkUserPermission(UserEntity user);
    Result checkUser(UserEntity user);
}
