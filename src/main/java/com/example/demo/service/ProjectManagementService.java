package com.example.demo.service;

import com.example.demo.Entity.FileEntity;
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface ProjectManagementService {

    Result findAllProject(Integer limit, Integer offset, UserEntity operator);
    Result findAllProjectByProjectNameLike(Integer limit, Integer offset, ProjectEntity project, UserEntity operator);
    Result findAllInstitute();
   // Result findAllInstitute(Integer limit, Integer offset, UserEntity operator);
    Result deleteAllProjectsByProjectId(ArrayList<ProjectEntity> projectEntities,UserEntity operator);
    Result addProject(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectName(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectType(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectManager(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectLevel(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectProgress(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectSource(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectEstablishDate(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectPlannedDate(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectLaunchDate(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectFinishDate(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectFund(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectSourceType(ProjectEntity project,UserEntity operator);
    Result updateProjectSetProjectResearchType(ProjectEntity project,UserEntity operator);
    Result findAllUserByUserIdLike(UserEntity userEntity,UserEntity operator);
    Result findAllUserByUserNameLike(UserEntity userEntity,UserEntity operator);
    Result updateProjectMembers(ProjectEntity project,UserEntity operator);
    Result addFile(FileEntity file,UserEntity operator);
    Result deleteFile(FileEntity file,UserEntity operator);
}
