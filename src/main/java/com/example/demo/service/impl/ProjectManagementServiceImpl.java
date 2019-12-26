package com.example.demo.service.impl;

import com.example.demo.Entity.FileEntity;
import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProjectManagementService;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private FileRepository fileRepository;
    @Override
    public Result findAllProject(Integer limit, Integer offset, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        ArrayList<ProjectEntity> projectEntities = projectRepository.findAll();
        /*
        分页
         */
        int index = offset * limit;
        ArrayList<ProjectEntity> projectEntityArrayList = new ArrayList<ProjectEntity>();
        for(int i = index ; i<index+limit;i++){
            if(i>=projectEntities.size()){
                break;
            }
            projectEntityArrayList.add(projectEntities.get(i));
        }
        result = new Result(true);
        result.addObject("rows",projectEntityArrayList);
        result.addObject("total",projectEntities.size());
        return result;
    }

    @Override
    public Result findAllProjectByProjectNameLike(Integer limit, Integer offset, ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        if(project==null||project.getProjectName()==null||project.getProjectName().equals("")){
            return new Result(false,"projectName is empty");
        }
        String projectName = "%" + project.getProjectName() + "%";
        ArrayList<ProjectEntity> projectEntities = projectRepository.findAllByProjectNameLike(projectName);

        int index = offset * limit;
        ArrayList<ProjectEntity> projectEntityArrayList = new ArrayList<ProjectEntity>();
        for(int i = index ; i<index+limit;i++){
            if(i>=projectEntities.size()){
                break;
            }
            projectEntityArrayList.add(projectEntities.get(i));
        }
        result = new Result(true);
        result.addObject("rows",projectEntityArrayList);
        result.addObject("total",projectEntities.size());
        return result;
    }

    @Override
    public Result findAllInstitute(Integer limit, Integer offset, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        ArrayList<InstituteEntity> instituteEntities = instituteRepository.findAll();

        int index = offset * limit;
        ArrayList<InstituteEntity> instituteEntityArrayList = new ArrayList<InstituteEntity>();
        for(int i = index ; i<index+limit;i++){
            if(i>=instituteEntities.size()){
                break;
            }
            instituteEntityArrayList.add(instituteEntities.get(i));
        }
        result = new Result(true);
        result.addObject("rows",instituteEntityArrayList);
        result.addObject("total",instituteEntities.size());
        return result;
    }

    @Override
    public Result deleteAllProjectsByProjectId(ArrayList<ProjectEntity> projectEntities, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        for(int i = 0; i < projectEntities.size();i++){
            ProjectEntity project = projectEntities.get(i);
            if(project==null||project.getProjectId()==null||project.getProjectId().equals("")){
                return new Result(false,"project not exist,index = " + i);
            }
            ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
            if(projectEntity!=null){
                projectRepository.delete(projectEntity);
            }
        }
        return new Result(true,"delete success");
    }

    @Override
    public Result addProject(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        /*
        检查项目信息
         */
        if(project==null){
            return new Result(false,"project not exist");
        }
        if(project.getProjectId()==null||project.getProjectId().equals("")){
            return new Result(false,"project not exist");
        }
        result = checkProjectManager(project.getProjectManager());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectName(project.getProjectName());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectEstablishDate(project.getProjectEstablishDate());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectFinishDate(project.getProjectFinishDate());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectFund(project.getProjectFund());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectLaunchDate(project.getProjectLaunchDate());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectLevel(project.getProjectLevel());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectPlannedDate(project.getProjectPlannedDate());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectProcess(project.getProjectProcess());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectResearchType(project.getProjectResearchType());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectSource(project.getProjectSource());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectSourceType(project.getProjectSourceType());
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectType(project.getProjectType());
        if(!result.isSuccess()){
            return result;
        }
        projectRepository.save(project);
        return new Result(true);
    }

    @Override
    public Result updateProjectSetProjectName(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectName(project.getProjectName());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectName(project.getProjectName());
        projectRepository.save(projectEntity);
        return new Result(true,"project name update successfully");
    }

    @Override
    public Result updateProjectSetProjectType(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectType(project.getProjectType());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectManager(project.getProjectManager());
        projectRepository.save(projectEntity);
        return new Result(true,"project type update successfully");
    }

    @Override
    public Result updateProjectSetProjectManager(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectManager(project.getProjectManager());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectType(project.getProjectType());
        projectRepository.save(projectEntity);
        return new Result(true,"project manager update successfully");
    }

    @Override
    public Result updateProjectSetProjectLevel(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectLevel(project.getProjectLevel());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectLevel(project.getProjectLevel());
        projectRepository.save(projectEntity);
        return new Result(true,"project level update successfully");
    }

    @Override
    public Result updateProjectSetProjectProgress(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectProcess(project.getProjectProcess());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectProcess(project.getProjectProcess());
        projectRepository.save(projectEntity);
        return new Result(true,"project level update successfully");
    }

    @Override
    public Result updateProjectSetProjectSource(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectSource(project.getProjectSource());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectSource(project.getProjectSource());
        projectRepository.save(projectEntity);
        return new Result(true,"project source update successfully");
    }

    @Override
    public Result updateProjectSetProjectEstablishDate(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectEstablishDate(project.getProjectEstablishDate());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectEstablishDate(project.getProjectEstablishDate());
        projectRepository.save(projectEntity);
        return new Result(true,"project establish time update successfully");
    }

    @Override
    public Result updateProjectSetProjectPlannedDate(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectPlannedDate(project.getProjectPlannedDate());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectPlannedDate(project.getProjectPlannedDate());
        projectRepository.save(projectEntity);
        return new Result(true,"project planned time update successfully");
    }

    @Override
    public Result updateProjectSetProjectLaunchDate(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectLaunchDate(project.getProjectLaunchDate());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectLaunchDate(project.getProjectLaunchDate());
        projectRepository.save(projectEntity);
        return new Result(true,"project launch time update successfully");
    }

    @Override
    public Result updateProjectSetProjectFinishDate(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectFinishDate(project.getProjectFinishDate());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectFinishDate(project.getProjectFinishDate());
        projectRepository.save(projectEntity);
        return new Result(true,"project finish time update successfully");
    }

    @Override
    public Result updateProjectSetProjectFund(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectFund(project.getProjectFund());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectFund(project.getProjectFund());
        projectRepository.save(projectEntity);
        return new Result(true,"project fund update successfully");
    }

    @Override
    public Result updateProjectSetProjectSourceType(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectSourceType(project.getProjectSourceType());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectSourceType(project.getProjectSourceType());
        projectRepository.save(projectEntity);
        return new Result(true,"project source type update successfully");
    }

    @Override
    public Result updateProjectSetProjectResearchType(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectResearchType(project.getProjectResearchType());
        if(!result.isSuccess()){
            return result;
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectResearchType(project.getProjectResearchType());
        projectRepository.save(projectEntity);
        return new Result(true,"project research type update successfully");
    }

    @Override
    public Result findAllUserByUserIdLike(UserEntity userEntity, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        if(userEntity==null||userEntity.getUserId()==null||userEntity.getUserId().equals("")){
            return new Result(false,"userId is empty");
        }
        String userId = "%" + userEntity.getUserId() + "%";
        ArrayList<UserEntity> userEntityArrayList = userRepository.findAllByUserIdLike(userId);
        result = new Result(true);
        result.addObject("userEntityArrayList",userEntityArrayList);
        return result;
    }

    @Override
    public Result findAllUserByUserNameLike(UserEntity userEntity, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        if(userEntity==null||userEntity.getUserName()==null||userEntity.getUserName().equals("")){
            return new Result(false,"userName is empty");
        }
        String userName = "%" + userEntity.getUserName() + "%";
        ArrayList<UserEntity> userEntityArrayList = userRepository.findAllByUserNameLike(userName);
        result = new Result(true);
        result.addObject("userEntityArrayList",userEntityArrayList);
        return result;
    }

    @Override
    public Result updateProjectMembers(ProjectEntity project, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkProjectExistence(project);
        if(!result.isSuccess()){
            return result;
        }
        for(UserEntity userEntity : project.getMembers()){
            if(userEntity==null||userEntity.getUserId()==null||userEntity.getUserId().equals("")){
                return new Result(false,"user not exists");
            }
            UserEntity user = userRepository.findByUserId(userEntity.getUserId());
            if(user==null||user.getUserStatus()==Const.UserStatus.DELETED){
                return new Result(false,"user not exists");
            }
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setMembers(project.getMembers());
        projectRepository.save(projectEntity);
        return new Result( true);
    }

    @Override
    public Result addFile(FileEntity file, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkFile(file);
        if(!result.isSuccess()){
            return result;
        }
        file.setFileStatus(Const.FileStatus.NORMAL);
        fileRepository.save(file);
        return new Result(true);
    }

    @Override
    public Result deleteFile(FileEntity file, UserEntity operator) {
        Result result = checkUser(operator);
        if(!result.isSuccess()){
            return result;
        }
        result = checkFile(file);
        if(!result.isSuccess()){
            return result;
        }
        FileEntity fileEntity = fileRepository.findByFileId(file.getFileId());
        if(fileEntity==null){
            return new Result(true);
        }
        fileEntity.setFileStatus(Const.FileStatus.DELETED);
        fileRepository.save(fileEntity);
        return new Result(true);
    }

    private Result checkUser(UserEntity operator){
        if(operator==null||operator.getUserId()==null){
            return new Result(false,"user not exist");
        }
        UserEntity userEntity = userRepository.findByUserId(operator.getUserId());
        if(userEntity==null){
            return new Result(false,"user not exist");
        }
        if(userEntity.getUserStatus()== Const.UserStatus.DELETED){
            return new Result(false,"user not exist");
        }
        if(userEntity.getCharacters()==Const.UserCharacter.NORMAL_USER){
            return new Result(false,"permission denied");
        }
        return new Result(true,"");
    }

    private Result checkProjectExistence(ProjectEntity project){
        if(project==null){
            return new Result(false,"project not exist");
        }
        if(project.getProjectId()==null||project.getProjectId().equals("")){
            return new Result(false,"project not exist");
        }
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        if(projectEntity==null){
            return new Result(false,"project not exist");
        }
        return new Result(true);
    }

    private Result checkProjectName(String projectName){
        if(projectName==null){
            return new Result(false,"project name is empty");
        }
        if(projectName.length()>255){
            return new Result(false,"project name is too long");
        }
        return new Result(true);
    }

    private Result checkProjectType(Const.ProjectType projectType){
        if(projectType==null){
            return new Result(false,"project type is empty");
        }
        return new Result(true);
    }

    private Result checkProjectManager(UserEntity projectManager){
        if(projectManager==null||projectManager.getUserId()==null||projectManager.getUserId().equals("")){
            return new Result(false,"user not exist");
        }
        UserEntity userEntity = userRepository.findByUserId(projectManager.getUserId());
        if(userEntity==null||userEntity.getUserStatus()==Const.UserStatus.DELETED){
            return new Result(false,"user not exist");
        }
        if(userEntity.getCharacters()!=Const.UserCharacter.TEACHER){
            return new Result(false,"user character error");
        }
        return new Result(true);
    }

    private Result checkProjectLevel(Const.ProjectLevel projectLevel){
        if(projectLevel==null){
            return new Result(false,"project level is empty");
        }
        return new Result(true);
    }

    private Result checkProjectProcess(Const.ProjectProgress projectProgress){
        if(projectProgress==null){
            return new Result(false,"project level is empty");
        }
        return new Result(true);
    }

    private Result checkProjectSource(String projectSource){
        if(projectSource==null||projectSource.equals("")){
            return new Result(false,"project source is empty");
        }
        return new Result(true);
    }

    private Result checkProjectEstablishDate(Date projectEstablishDate){
        if(projectEstablishDate==null){
            return new Result(false,"project date is empty");
        }
        return new Result(true);
    }

    private Result checkProjectPlannedDate(Date projectPlannedDate){
        if(projectPlannedDate==null){
            return new Result(false,"project date is empty");
        }
        return new Result(true);
    }

    private Result checkProjectLaunchDate(Date projectLaunchDate){
        if(projectLaunchDate==null){
            return new Result(false,"project date is empty");
        }
        return new Result(true);
    }

    private Result checkProjectFinishDate(Date projectFinishDate){
        if(projectFinishDate==null){
            return new Result(false,"project date is empty");
        }
        return new Result(true);
    }

    private Result checkProjectFund(Integer projectFund){
        if(projectFund==null||projectFund<0){
            return new Result(false,"invalid fund");
        }
        return new Result(true);
    }

    private Result checkProjectSourceType(Const.ProjectSourceType projectSourceType){
        if(projectSourceType==null){
            return new Result(false,"project source type is empty");
        }
        return new Result(true);
    }

    private Result checkProjectResearchType(Const.ProjectResearchType projectResearchType){
        if(projectResearchType==null){
            return new Result(false,"project source type is empty");
        }
        return new Result(true);
    }

    private Result checkFile(FileEntity file){
        if(file==null){
            return new Result(false,"file entity is null");
        }
        if(file.getProject()==null||file.getProject().getProjectId()==null||file.getProject().getProjectId().equals("")){
            return new Result(false,"wrong project");
        }
        if(projectRepository.findByProjectId(file.getProject().getProjectId())==null){
            return new Result(false,"project not exists");
        }
        return new Result(true);
    }
}
