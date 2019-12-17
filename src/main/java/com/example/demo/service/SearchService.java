package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;

import java.util.ArrayList;
import java.util.Date;

public interface SearchService {
    //region 论文搜索

    /**
     * 查找完整的论文列表
     * @param userEntity
     * @return 论文列表
     */
    Result<ArrayList<ThesisEntity>> findAllThesis(UserEntity userEntity);

    /**
     * 通过作者名字模糊查找论文
     * @param authorName
     * @param userEntity
     * @return
     */
    Result<ArrayList<ThesisEntity>> findAllThesisByAuthorLike(String authorName,UserEntity userEntity);
    Result<ArrayList<ThesisEntity>> findAllThesisByTitleLike(String title,UserEntity userEntity);
    Result<ThesisEntity> findThesisById(String id,UserEntity userEntity);
    Result<ArrayList<ThesisEntity>> findAllThesisByThesisStatus(Const.ThesisStatus thesisStatus,UserEntity userEntity);
    //endregion

    //region 著作搜索
    Result<ArrayList<BookEntity>> findAllBook(UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByAuthorLike(String authorName,UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByNameLike(String name,UserEntity userEntity);
    Result<BookEntity> findBookById(String id,UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByPublishStatus(Const.BookPublishStatus publishStatus, UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByCreativeNature(Const.BookCreativeNature creativeNature,UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByBookStatus(Const.BookStatus bookStatus,UserEntity userEntity);
    Result<ArrayList<BookEntity>> findAllBookByAuthorNo(String authorNo,UserEntity userEntity);//用于查询自己的著作
    //endregion

    //region 项目搜索
    Result<ArrayList<ProjectEntity>> findAllProject(UserEntity userEntity);
    Result<ProjectEntity> findProjectById(String id,UserEntity userEntity);
    Result<ProjectEntity> findProjectByCode(String code,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByIdLike(String projectId,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByCodeLike(String projectCode,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectNameLike(String projectName,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectType(Const.ProjectType projectType,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectManagerId(String id,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectLevel(Const.ProjectLevel projectLevel,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectProgress(Const.ProjectProgress projectProgress,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectFundGreaterThan(Integer projectFund,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectFundLessThanOrEquals(Integer projectFund,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectSourceType(Const.ProjectSourceType projectSourceType,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectResearchType(Const.ProjectResearchType projectResearchType,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectFinishTimeGreaterThan(Date finishTime,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectFinishTimeLessThanOrEquals(Date finishTime,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectEstablishDateGreaterThan(Date establishDate,UserEntity userEntity);
    Result<ArrayList<ProjectEntity>> findAllProjectByProjectEstablishDateLessThanOrEquals(Date establishDate,UserEntity userEntity);
    //endregion

    //region 用户搜索
    Result<UserEntity> findUserByUserId(String userId,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByUserIdLike(String userId,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByUserNameLike(String userName,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByTitle(Const.UserTitle userTitle,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByInstitute(InstituteEntity instituteEntity,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByCharacters(Const.UserCharacter userCharacter,UserEntity userEntity);
    Result<ArrayList<UserEntity>> findAllUserByUserStatus(Const.UserStatus userStatus,UserEntity userEntity);
    //endregion
}
