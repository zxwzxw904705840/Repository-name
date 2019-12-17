package com.example.demo.service;

import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Result;

public interface AdminService {
    //region 用户管理
    Result addUser(UserEntity userEntity,UserEntity admin);
    Result updateUserInformation(UserEntity userEntity,UserEntity admin);
    Result deleteUser(UserEntity userEntity,UserEntity admin);
    Result reviewUser(UserEntity userEntity,boolean pass,UserEntity admin);
    //endregion

    //region 论文管理
    Result addThesis(ThesisEntity thesisEntity, UserEntity admin);
    Result updateThesis(ThesisEntity thesisEntity, UserEntity admin);
    Result deleteThesis(ThesisEntity thesisEntity, UserEntity admin);
    Result thesisUser(ThesisEntity thesisEntity,boolean pass,UserEntity admin);
    //endregion

    //region 著作管理
    Result addBook(BookEntity bookEntity, UserEntity admin);
    Result updateBook(BookEntity bookEntity, UserEntity admin);
    Result deleteBook(BookEntity bookEntity, UserEntity admin);
    Result bookUser(BookEntity bookEntity,boolean pass,UserEntity admin);
    //endregion

    //region 我的项目操作
    Result addProject(ProjectEntity projectEntity, UserEntity admin);
    Result updateProject(ProjectEntity projectEntity, UserEntity admin);
    Result deleteProject(ProjectEntity projectEntity, UserEntity admin);
    //endregion
}
