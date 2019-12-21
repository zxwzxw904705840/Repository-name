package com.example.demo.service.impl;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Const;
import com.example.demo.utils.DataCheck;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    InstituteRepository instituteRepository;

//    Result addUserExcel(UserEntity user);
//    Result UserExcelExport();

    public List<UserEntity> getUserList(){
        return userRepository.findAll();
    }

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

    public Result getUserById(String userId, UserEntity admin){
        Result result = checkUserPermission(admin);
        if(!result.isSuccess()){
            return result;
        }
        UserEntity userEntity = userRepository.findByUserId(userId);
        result.addObject(userId,userEntity);
        return result;
    }

    public Result AgreeUserUpdate(UserEntity user, UserEntity admin){
        Result resultuser = checkUser(user);
        Result resultadmin = checkUserPermission(admin);
        if(!resultuser.isSuccess()){
            return resultuser;
        }
        if(!resultadmin.isSuccess()){
            return resultadmin;
        }
        user.setUserStatus(Const.UserStatus.NORMAL);
        return new Result(true, DataCheck.UserDataCheck.USER_STATUS_UPDATED.toString());
    }

    public Result DeleteUserById(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setUserStatus(Const.UserStatus.DELETED);
        return new Result(true, DataCheck.UserDataCheck.USER_DELETED.toString());
    }

    public Result SetUserName(String userId, String userName){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){ return result; }
        userEntity.setUserName(userName);
        return new Result(true, DataCheck.UserDataSet.USERNAME_CHANGED.toString());
    }

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

    public Result SetTitle(String userId, Const.UserTitle title){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setTitle(title);
        return new Result(true,DataCheck.UserDataSet.USER_TITLE_CHANGED.toString());
    }

    public Result SetEmail(String userId,String email){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setEmail(email);
        return new Result(true, DataCheck.UserDataSet.USER_EMAIL_CHANGED.toString());
    }

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
    private Result checkInstitute(InstituteEntity institute){
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

}
