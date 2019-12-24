package com.example.demo.service.impl;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.DataCheck;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    InstituteRepository instituteRepository;

    //region 获得用户部分
    @Override
    public List<UserEntity> getUserList(){
        return userRepository.findAll();
    }

    @Override
    public Result getUserById(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        result.addObject(userId,userEntity);
        return result;
    }

    @Override
    public List<UserEntity> getUsersById(List<String> userId){
        List<UserEntity> users = userRepository.findAllById(userId);
        return users;
    }

    @Override
    public List<UserEntity> getUsersByStatus(Const.UserStatus status, String adminId){
        UserEntity adminEntity = userRepository.findByUserId(adminId);
        Result result = checkUserPermission(adminEntity);
        if(result.getMessage() != DataCheck.UserDataCheck.IS_ADMIN.toString()){
            return null;
        }
        List<UserEntity> usersByStatus = userRepository.findUserEntitiesByUserStatus(status);
        return usersByStatus;
    }

    public List<UserEntity> getUsersByRole(Const.UserCharacter role){
        List<UserEntity> usersByRole = userRepository.findUserEntitiesByCharacters(role);
        return usersByRole;
    }
    //endregion

    //region 添加新用户
    @Override
    public Result addUser(UserEntity user){
        UserEntity newUser = new UserEntity();
        Result result = checkUser(user);

        if(result.getMessage().contentEquals(DataCheck.UserDataCheck.USER_NOT_EXISTS.toString())){
            user.setUserStatus(Const.UserStatus.REVIEWING);
            userRepository.save(user);
            System.out.println(result.getMessage());
            return new Result(true, "新用户添加了！");
        }
        System.out.println(result.getMessage());
        return new Result(false, "添加新用户出问题，　用户存在！");
    }

    @Override
    public Result addUsers(List<UserEntity> users){
        Result result;
        for(int i = 0; i <= users.size(); i++){
            result = checkUser(users.get(i));
            if (!result.isSuccess()){
                return result;
            }
            users.get(i).setUserStatus(Const.UserStatus.REVIEWING);
            userRepository.save(users.get(i));
        }
        return new Result(true, DataCheck.UserDataCheck.USERS_ADDED.toString());
    }
    //endregion

    //region 删除用户函数
    @Override
    public Result DeleteUserById(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setUserStatus(Const.UserStatus.DELETED);
        return new Result(true, DataCheck.UserDataCheck.USER_DELETED.toString());
    }

    @Override
    public Result DeleteUsersById(String[] userId){
        for(int i = 0; i <= userId.length; i++){
            UserEntity userEntity = userRepository.findByUserId(userId[i]);
            Result result = checkUser(userEntity);
            if(!result.isSuccess()){
                return result;
            }
            userEntity.setUserStatus(Const.UserStatus.DELETED);
        }
        return new Result(true, DataCheck.UserDataCheck.USERS_DELETED.toString());
    }

    @Override
    public Result DeleteCompletely(String userId, UserEntity admin){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result adminresult = checkUserPermission(admin);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(adminresult.getMessage() != DataCheck.UserDataCheck.IS_ADMIN.toString()){
            return adminresult;
        }
        userRepository.delete(userEntity);
        return new Result(true,DataCheck.UserDataSet.USER_DELETED.toString());
    }

    @Override
    public Result DeleteUsersCompletely(List<UserEntity> users, UserEntity personCanDelete){
        Result result = checkUserPermission(personCanDelete);
        if(result.getMessage() == DataCheck.UserDataCheck.IS_ADMIN.toString())
            userRepository.deleteAll(users);
//        for(int i = 0; i <= users.size(); i++){
//            result = checkUser(users.get(i));
//            if (!result.isSuccess()){
//                return result;
//            }
//            userRepository.deleteAll(users.get(i));
//        }
        return new Result(true, DataCheck.UserDataCheck.USERS_DELETED.toString());
    }
    //endregion

    //region 修改用户信息
    @Override
    public Result SetUserName(String userId, String userName){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){ return result; }
        userEntity.setUserName(userName);
        return new Result(true, DataCheck.UserDataSet.USERNAME_CHANGED.toString());
    }

    @Override
    public Result SetInstituteId(String userId,String instituteId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        InstituteEntity instituteEntity = instituteRepository.findInstituteEntityByInstituteId(instituteId);
        Result result = checkUser(userEntity);
        Result instresult = checkInstitute(instituteEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(!instresult.isSuccess()){
            return instresult;
        }
        userEntity.setInstitute(instituteEntity);
        return new Result(true, DataCheck.UserDataSet.INSTITUTE_CHANGED.toString());
    }

    @Override
    public Result SetTitle(String userId, Const.UserTitle title){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setTitle(title);
        return new Result(true,DataCheck.UserDataSet.USER_TITLE_CHANGED.toString());
    }

    @Override
    public Result SetEmail(String userId,String email){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setEmail(email);
        return new Result(true, DataCheck.UserDataSet.USER_EMAIL_CHANGED.toString());
    }

    @Override
    public Result SetPassword(String userId,String password){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(password==null||password.equals("")){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_IS_EMPTY.toString());
        }
        if(password.length()<6){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_TOO_SHORT.toString());
        }
        if(password.length()>16){
            return new Result(false, DataCheck.UserDataCheck.PASSWORD_TOO_LONG.toString());
        }
        userEntity.setPassword(password);
        return new Result(true, DataCheck.UserDataSet.USER_PASSWORD_CHANGED.toString());
    }

    @Override
    public Result SetPhone(String userId,String phone){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(phone==null||phone.equals("")){
            return new Result(false, DataCheck.UserDataCheck.PHONE_IS_EMPTY.toString());
        }
        if(phone.length()!=11){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
        try{
            System.out.println(Integer.parseInt("123456789"));
            //Integer.parseInt(user.getPhone());
        }catch (Exception e){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
        userEntity.setPhone(phone);
        return new Result(true, DataCheck.UserDataSet.USER_PHONE_CHANGED.toString());
    }

    @Override
    public Result SetStatus(String userId, UserEntity admin, Const.UserStatus status){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result adminresult = checkUserPermission(admin);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(adminresult.getMessage() != DataCheck.UserDataCheck.IS_ADMIN.toString()){
            return adminresult;
        }
        if(status != Const.UserStatus.DELETED && status != Const.UserStatus.NORMAL && status != Const.UserStatus.REVIEWING){
            return new Result(false, "用户状态不存在");
        }
        userEntity.setUserStatus(status);
        return new Result(true, DataCheck.UserDataSet.USER_STATUS_CHANGED.toString());

    }
    //endregion

    //region 检查函数部分
    @Override
    public Result checkUser(UserEntity user){
        if (user==null){
            return new Result(false, DataCheck.UserDataCheck.USER_NOT_EXISTS.toString());
        }
        if(user.getUserId()==null||user.getUserId().equals("")){
            return new Result(false, DataCheck.UserDataCheck.USER_ID_IS_EMPTY.toString());
        }
        if(user.getUserId().length()>10){
            return new Result(false, DataCheck.UserDataCheck.USER_ID_IS_TOO_LONG.toString());
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
            return new Result(false, DataCheck.UserDataCheck.USER_NOT_EXISTS_OR_STATUS_NOT_NORMAL.toString());
        }
        if(user.getTitle()==null){
            return new Result(false, DataCheck.UserDataCheck.EMPTY_TITLE.toString());
        }
        return new Result(true, DataCheck.UserDataCheck.ACCOUNT_CAN_USE.toString());
    }

    @Override
    public Result checkUserPermission(UserEntity user) {
        if(user==null||user.getUserId()==null||user.getUserId().equals("")) {
        return new Result(false, DataCheck.UserDataCheck.USER_ID_IS_EMPTY.toString());
    }
        if(user.getCharacters() == Const.UserCharacter.ADMINISTRATION){
            return new Result(true, DataCheck.UserDataCheck.IS_ADMIN.toString());
        }
        if(user.getCharacters() == Const.UserCharacter.TEACHER){
            return new Result(true, DataCheck.UserDataCheck.IS_RESEARCHER.toString());
        }
        return new Result(false, DataCheck.UserDataCheck.PERMISSION_DENIED.toString());
    }

    @Override
    public Result checkInstitute(InstituteEntity institute){
        if (institute==null){
            return new Result(false, DataCheck.InstituteCheck.INSTITUTE_NOT_EXISTS.toString());
        }
        if(institute.getInstituteId()==null||institute.getInstituteId().equals("")){
            return new Result(false, DataCheck.InstituteCheck.INSTITUTE_ID_IS_EMPTY.toString());
        }
        if(institute.getInstituteName()==null||institute.getInstituteName().equals("")){
            return new Result(false, DataCheck.InstituteCheck.INSTITUTE_NAME_IS_EMPTY.toString());
        }
        return new Result(true, DataCheck.InstituteCheck.INSTITUTE_CAN_USE.toString());
    }
    //endregion
}