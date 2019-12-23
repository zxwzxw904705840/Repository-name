package com.example.demo.service;

import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Result;

import java.util.ArrayList;

public interface ResearchService {
    //region 我的论文操作
    Result addThesis(ThesisEntity thesisEntity, UserEntity userEntity);
    Result updateThesis(ThesisEntity thesisEntity, UserEntity userEntity);
    Result deleteThesis(ThesisEntity thesisEntity, UserEntity userEntity);
   // Result<ArrayList<ThesisEntity>> findAllThesisByAuthorId(UserEntity userEntity);
    //endregion

    //region 我的著作操作
    Result addBook(BookEntity bookEntity, UserEntity userEntity);
    Result updateBook(BookEntity bookEntity, UserEntity userEntity);
    Result deleteBook(BookEntity bookEntity, UserEntity userEntity);
   // Result<ArrayList<BookEntity>> findAllBookByAuthorId(UserEntity userEntity);
    //endregion

    //region 我的项目操作
    Result addProject(ProjectEntity projectEntity, UserEntity userEntity);
    Result updateProject(ProjectEntity projectEntity, UserEntity userEntity);
    Result deleteProject(ProjectEntity projectEntity, UserEntity userEntity);
  //  Result<ArrayList<ProjectEntity>> findAllProjectByUserId(UserEntity userEntity);
    //endregion

    //region 我的信息操作
    Result updateMyInformation(UserEntity userEntity);
    UserEntity findUserByUserId(String userId);
    //endregion
}
