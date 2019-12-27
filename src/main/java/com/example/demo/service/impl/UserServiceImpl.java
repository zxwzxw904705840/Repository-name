package com.example.demo.service.impl;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.example.demo.utils.Const;
import com.example.demo.utils.DataCheck;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private ThesisRepository thesisRepository;
    @Autowired
    private BookRepository bookRepository;



    @Override
    public ArrayList<UserEntity> findByserStatusNot(Const.UserStatus userStatus) {
        return userRepository.findByUserStatusNot(Const.UserStatus.DELETED);
    }

    //region User部分
    //region 获得用户

    /**
     * 返回整个用户的列表
     *
     * @param
     * @return
     */

    @Override
    public List<UserEntity> getUserList(){
        return userRepository.findAll();
    }

    /**
     * 返回用户列表， 按照下术有些可能的情况来返回；
     *
     * 1-USERNAME: !NULL, USERSTATUS: NULL, INSTITUTE: NULL
     * 2-USERNAME: !NULL, USERSTATUS: !NULL, INSTITUTE: NULL
     * 3-USERNAME: !NULL, USERSTATUS: NULL, INSTITUTE: !NULL
     * 4-USERNAME: !NULL, USERSTATUS: !NULL, INSTITUTE: !NULL
     * 5-USERNAME: NULL, USERSTATUS: !NULL, INSTITUTE: !NULL
     * 6-USERNAME: NULL, USERSTATUS: !NULL, INSTITUTE: NULL
     * 7-USERNAME: NULL, USERSTATUS: !NULL, INSTITUTE: NULL
     * 8-USERNAME: NULL, USERSTATUS: NULL, INSTITUTE: !NULL
     * 9-USERNAME: NULL, USERSTATUS: NULL, INSTITUTE: NULL
     *
     * @param userName
     * @param ustatus
     * @param instituteEntity
     * @return
     */
    @Override
    public List<UserEntity> getUserList(String userName, Const.UserStatus ustatus, InstituteEntity instituteEntity){
        // USERNAME: !NULL, USERSTATUS: NULL, INSTITUTE: NULL
        if(!userName.equals("") && ustatus.toString().equals("") && instituteEntity == null){
            return userRepository.findByUserNameContaining(userName);
        }

        // USERNAME: !NULL, USERSTATUS: !NULL, INSTITUTE: NULL
        if(!userName.equals("") && !ustatus.toString().equals("") && instituteEntity == null){
            return userRepository.findAllByUserNameContainingAndUserStatusIs(userName, ustatus);
        }

        // USERNAME: !NULL, USERSTATUS: NULL, INSTITUTE: !NULL
        if(!userName.equals("") && ustatus.toString().equals("") && !(instituteEntity == null)){
            return userRepository.findAllByUserNameContainingAndInstituteIs(userName,instituteEntity);
        }

        // USERNAME: !NULL, USERSTATUS: !NULL, INSTITUTE: !NULL
        if(!userName.equals("") && !ustatus.toString().equals("") && !(instituteEntity == null)){
            return userRepository.findAllByUserNameContainingAndUserStatusIsAndInstituteIs(userName, ustatus, instituteEntity);
        }

        // USERNAME: NULL, USERSTATUS: !NULL, INSTITUTE: !NULL
        if(userName.equals("") && !ustatus.toString().equals("") && !(instituteEntity == null)){
            return userRepository.findAllByUserStatusIsAndInstituteIs(ustatus,instituteEntity);
        }

        // USERNAME: NULL, USERSTATUS: !NULL, INSTITUTE: NULL
        if(userName.equals("") && !ustatus.toString().equals("") && (instituteEntity == null)){
            return userRepository.findAllByUserStatusIs(ustatus);
        }

        // USERNAME: NULL, USERSTATUS: NULL, INSTITUTE: !NULL
        if(userName.equals("") && ustatus.toString().equals("") && !(instituteEntity == null)){
            return userRepository.findAllByInstituteIs(instituteEntity);
        }

        // USERNAME: NULL, USERSTATUS: NULL, INSTITUTE: NULL
        return getUserList();
    }

    /**
     * 返回用户实体   按照用户ID来找实体
     * @param userId
     * @return
     */
    @Override
    public UserEntity getUserById(String userId){
        return userRepository.findByUserId(userId);
    }

    /**
     * 返回用户实体   按照精确地用户名来找实体
     * @param userName
     * @return
     */
    @Override
    public UserEntity getUserByName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<UserEntity> getUsersById(List<String> userId){
        return userRepository.findAllById(userId);
    }

    /**
     * 返回用户实体列表    必要条件：
     *      1- status变量只能是Const里定义的
     *      2- 使用这个函数的用户是应该有管理员权限
     * @param status
     * @param adminId
     * @return
     */
    @Override
    public List<UserEntity> getUsersByStatus(Const.UserStatus status, String adminId){
        UserEntity adminEntity = userRepository.findByUserId(adminId);
        Result result = checkUserPermission(adminEntity);
        if(!result.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return null;
        }
        return  userRepository.findUserEntitiesByUserStatus(status);
    }

    /**
     * 返回用户列表   按照精确的角色
     *    必要条件：
     *    管理员权限
     * @param role
     * @param adminId
     * @return
     */
    @Override
    public List<UserEntity> getUsersByRole(Const.UserCharacter role, String adminId){
        UserEntity adminEntity = userRepository.findByUserId(adminId);
        Result result = checkUserPermission(adminEntity);
        if(!result.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return null;
        }
        return userRepository.findUserEntitiesByCharacters(role);
    }
    //endregion

    //region 添加新用户

    /**
     * 返回Result类型的实体    按照用户实体添加新用户
     * @param user
     * @return
     */
    @Override
    @Transactional
    public Result addUser(UserEntity user){
        Result result = checkUser(user);
        if(!result.isSuccess()){
            return result;
        }
        user.setUserStatus(Const.UserStatus.REVIEWING);
        userRepository.save(user);
        return new Result(true, "新用户添加了！");
    }

    /**
     * 返回Result类型的实体    按照用户实体的列表来添加
     * @param users
     * @return
     */
    @Override
    @Transactional
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

    /**
     * 返回Result类型的实体    删除功能想法：
     *     1- 使用用户ID参数，找相关的实体；
     *     2- 检查用户信息的合适性；
     *     3- 把实体的状态改成为DELETED(已删除了)：
     *         不是从数据库删掉。
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result DeleteUserById(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setUserStatus(Const.UserStatus.DELETED);
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataCheck.USER_DELETED.toString());
    }

    /**
     * 返回Result类型的实体    删除功能想法：
     *      1- 使用用户ID数组，找相关的实体数组；
     *      2- 检查数组里的所有用户信息的合适性；
     *      3- 把所有用户实体的状态属性改成为DELETED(已删除了)：
     *          不是从数据库删掉。
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Result DeleteUsersById(String[] userId){
        for(int i = 0; i < userId.length; i++){
            UserEntity userEntity = userRepository.findByUserId(userId[i]);
            Result result = checkUser(userEntity);
            if(!result.isSuccess()){
                return result;
            }
            userEntity.setUserStatus(Const.UserStatus.DELETED);
            userRepository.save(userEntity);
        }
        return new Result(true, DataCheck.UserDataCheck.USERS_DELETED.toString());
    }

    /**
     * 返回Result类型的实体    删除功能想法：
     *      1- 使用用户ID参数，找相关的实体；
     *      2- 检查用户信息的合适性；
     *      3- 从数据库把相关的数据删掉
     * @param userId
     * @param admin
     * @return
     */
    @Override
    @Transactional
    public Result DeleteCompletely(String userId, UserEntity admin){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result adminresult = checkUserPermission(admin);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(!adminresult.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return adminresult;
        }
        userRepository.delete(userEntity);
        return new Result(true,DataCheck.UserDataSet.USER_DELETED.toString());
    }

    /**
     * 返回Result类型的实体    删除功能想法：
     *      1- 使用用户ID数组，找相关的实体数组；
     *      2- 检查数组里的所有用户信息的合适性；
     *      3- 从数据库把所有相关的用户数据删掉。
     *
     * @param users
     * @param personCanDelete
     * @return
     */
    @Override
    @Transactional
    public Result DeleteUsersCompletely(List<UserEntity> users, UserEntity personCanDelete){
        Result result = checkUserPermission(personCanDelete);
        if(result.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString()))
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

    /**
     * 返回Result类型的实体   按照用户ID找用户实体而改名字
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     名字按照userName参数改，返回成功信息。
     * @param userId
     * @param userName
     * @return
     */
    @Override
    @Transactional
    public Result SetUserName(String userId, String userName){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){ return result; }
        userEntity.setUserName(userName);
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.USERNAME_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，按照学院ID找学院实体；
     *     从数据库里找出来相关的用户实体；
     *     从数据库里找出来相关的学院实体；
     *     检查用户信息的合适性；
     *     检查学院信息的合适性；
     *     用户的学院按照学院ID参数来改。
     * @param userId
     * @param instituteId
     * @return
     */
    @Override
    @Transactional
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
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.INSTITUTE_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，而改用户称谓
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     用户称谓按照title参数改，返回成功信息。
     * @param userId
     * @param title
     * @return
     */
    @Override
    @Transactional
    public Result SetTitle(String userId, Const.UserTitle title){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setTitle(title);
        userRepository.save(userEntity);
        return new Result(true,DataCheck.UserDataSet.USER_TITLE_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，而改电子邮件
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     用户电子邮件信息按照email参数改，返回成功信息。
     * @param userId
     * @param email
     * @return
     */
    @Override
    @Transactional
    public Result SetEmail(String userId,String email){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        userEntity.setEmail(email);
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.USER_EMAIL_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，而改用户密码
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     检查密码的合适性；
     *     用户密码按照password参数改，返回成功信息。
     * @param userId
     * @param password
     * @return
     */
    @Override
    @Transactional
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
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.USER_PASSWORD_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，而改用户手机信息
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     检查输入的手机号码合适性；
     *     用户称谓按照title参数改，返回成功信息。
     * @param userId
     * @param phone
     * @return
     */
    @Override
    @Transactional
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
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.USER_PHONE_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体   按照用户ID找用户实体，而改用户权限
     *     必要条件： 管理员权限
     *     从数据库里找出来相关的用户实体；
     *     从数据库里找出相关的管理员实体；
     *     检查用户信息的合适性；
     *     判断是否是管理员权限；
     *     用户状态按照status参数改，返回成功信息。
     * @param userId
     * @param admin
     * @param status
     * @return
     */
    @Override
    @Transactional
    public Result SetStatus(String userId, UserEntity admin, Const.UserStatus status){
        UserEntity userEntity = userRepository.findByUserId(userId);

        Result adminresult = checkUserPermission(admin);
        Result result = checkUser(userEntity);
        if(!result.isSuccess()){
            return result;
        }
        if(adminresult.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return adminresult;
        }
        if(status != Const.UserStatus.DELETED && status != Const.UserStatus.NORMAL && status != Const.UserStatus.REVIEWING){
            return new Result(false, "用户状态不存在");
        }
        userEntity.setUserStatus(status);
        userRepository.save(userEntity);
        return new Result(true, DataCheck.UserDataSet.USER_STATUS_CHANGED.toString());

    }


    /**
     * 返回Result类型的实体   新用户添加被管理员的接受
     *     按照用户ID找用户实体，而改用户称谓
     *     从数据库里找出来相关的用户实体；
     *     检查用户信息的合适性；
     *     检查用户信息的合适性；
     *     判断是否是管理员权限；
     *     新用户被管理员接受（状态改为’正常‘）。
     * @param userId
     * @param adminId
     * @return
     */
    @Override
    @Transactional
    public Result AgreeUserUpdate(String userId, String adminId){
        UserEntity user = userRepository.findByUserId(userId);
        UserEntity admin = userRepository.findByUserId(adminId);
        Result resultuser = checkUser(user);
        Result resultadmin = checkUserPermission(admin);
        if(!resultuser.isSuccess()){
            return resultadmin;
        }
        if(!resultadmin.isSuccess()){
            return resultadmin;
        }
        if(resultadmin.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return new Result(false, DataCheck.UserDataCheck.PERMISSION_DENIED.toString());
        }
        user.setUserStatus(Const.UserStatus.NORMAL);
        userRepository.save(user);
        return new Result(true, DataCheck.UserDataCheck.USER_STATUS_UPDATED.toString());
    }
    //endregion

    //region 检查函数部分

    /**
     * 返回Result类型的实体 检查参数用户实体的以下条件；
     *     1-实体是否为空
     *     2-ID是否为空
     *     3-ID是否长于10个数字
     *     4-密码是否为空
     *     5-密码是否小于6个数字
     *     6-密码是否大于16个数字
     *     7-角色是否不期望的
     *     8-用户名是否为空
     *     9-手机号是否为空
     *     10-手机号是否不等于11个数字
     *     11-手机号字体字符串类型转换成整数类型是否存在任何不期望的字
     *     12-称谓是否为空
     * @param user
     * @return
     */
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
        /*if(user.getPhone()==null||user.getPhone().equals("")){
            return new Result(false, DataCheck.UserDataCheck.PHONE_IS_EMPTY.toString());
        }*/
        if(user.getPhone().length()!=11){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
        try{
            System.out.println(Integer.parseInt("123456789"));
            //Integer.parseInt(user.getPhone());
        }catch (Exception e){
            return new Result(false, DataCheck.UserDataCheck.ILLEGAL_TELEPHONE.toString());
        }
       /* if(user.getTitle()==null){

            return new Result(false, DataCheck.UserDataCheck.EMPTY_TITLE.toString());
        }*/
        return new Result(true, DataCheck.UserDataCheck.ACCOUNT_CAN_USE.toString());
    }

    /**
     * 返回Result类型的实体 检查参数用户实体的以下条件；
     *     1-用户实体是否为空；
     *     2-用户实体是否为管理员角色；若是，返回IS_ADMIN；
     *     3-用户实体是否为研究人员角色；若是，返回IS_RESEARCHER；
     *     4-以上都不是；返回权限不足(PERMISSION_DENIED)；
     * @param user
     * @return
     */
    @Override
    public Result checkUserPermission(UserEntity user) {
        if(user==null||user.getUserId()==null||user.getUserId().equals("")) {
            return new Result(false, DataCheck.UserDataCheck.USER_ID_IS_EMPTY.toString());
        }
        if(user.getCharacters() == Const.UserCharacter.ADMINISTRATION){
            return new Result(true, DataCheck.UserDataCheck.IS_ADMIN.toString()+"1");
        }
        if(user.getCharacters() == Const.UserCharacter.TEACHER){
            return new Result(true, DataCheck.UserDataCheck.IS_RESEARCHER.toString());
        }
        return new Result(false, DataCheck.UserDataCheck.PERMISSION_DENIED.toString());
    }

    /**
     * 返回Result类型的实体 检查参数学院实体的以下条件；
     *     1-学院实体是否为空；
     *     2-学院ID是否为空；
     *     3-学院名字是否为空。
     * @param institute
     * @return
     */
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

    //region 查询
    public List<UserEntity> findByUserNameLike(String username){
        return userRepository.findByUserNameLike(username);
    }

    public List<UserEntity> findByUserNameStartingWith(String username){
        return userRepository.findByUserNameStartingWith(username);
    }

    public List<UserEntity> findByUserNameEndingWith(String username){
        return userRepository.findByUserNameEndingWith(username);
    }

    //endregion
    //endregion

    //region 研究员(RESEARCHER)操作集

    //region 论文(THESIS)操作

    /**
     * 返回Result类型的实体    按照研究人员实体添加新论文
     * @param thesisEntity
     * @param researcher
     * @return
     */
    @Override
    @Transactional
    public Result addThesis(ThesisEntity thesisEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        thesisRepository.save(thesisEntity);
        return new Result(true, DataCheck.ThesisCheck.THESIS_ADDED.toString());
    }

    /**
     * 返回Result类型的实体    按照研究人员实体修改新论文
     * @param thesisEntity
     * @param researcher
     * @return
     */
    @Override
    @Transactional
    public Result updateThesis(ThesisEntity thesisEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        ThesisEntity t = thesisRepository.findByThesisId(thesisEntity.getThesisId());
        if(!result.isSuccess()){
            return result;
        }
        t.setThesisId(thesisEntity.getThesisId());
        t.setThesisTitle(thesisEntity.getThesisTitle());
        t.setAuthor1(thesisEntity.getAuthor1());
        t.setAuthor2(thesisEntity.getAuthor2());
        t.setAuthor3(thesisEntity.getAuthor3());
        t.setJournal(thesisEntity.getJournal());
        t.setVolume(thesisEntity.getVolume());
        t.setUrl(thesisEntity.getUrl());
        t.setPages(thesisEntity.getPages());
        t.setPrivacy(thesisEntity.getPrivacy());
        t.setStatus(thesisEntity.getStatus());
        thesisRepository.save(t);
        return new Result(true, DataCheck.ThesisCheck.THESIS_CHANGED.toString());
    }

    /**
     * 返回Result类型的实体    按照研究人员实体删除论文
     * @param thesisEntity
     * @param researcher
     * @return
     */
    @Override
    @Transactional
    public Result deleteThesis(ThesisEntity thesisEntity, UserEntity researcher) {
        Result result = checkUserPermission(researcher);
        if (result.isSuccess()) {
            ThesisEntity t = thesisRepository.findByThesisId(thesisEntity.getThesisId());
            t.setStatus(Const.ThesisStatus.DELETED);
            thesisRepository.save(t);
        }
        return new Result(true, DataCheck.ThesisCheck.THESIS_DELETED.toString());
    }

    /**
     * 返回论文实体列表类    按照研究人员ID查询所有的论文
     * 包括三个嵌套的查询：
     *     1-
     * @param authorId
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByAuthorId(String authorId){
        UserEntity author = userRepository.findByUserId(authorId);
        List<ThesisEntity> thesisListAuthor1 = thesisRepository.findAllByAuthor1AndStatusIsNot(author, Const.ThesisStatus.DELETED);
        List<ThesisEntity> thesisListAuthor2 = thesisRepository.findAllByAuthor2AndStatusIsNot(author, Const.ThesisStatus.DELETED);
        List<ThesisEntity> thesisListAuthor3 = thesisRepository.findAllByAuthor3AndStatusIsNot(author, Const.ThesisStatus.DELETED);
        List<ThesisEntity> finalList = new ArrayList<>();
        finalList.addAll(thesisListAuthor1);
        finalList.addAll(thesisListAuthor2);
        finalList.addAll(thesisListAuthor3);
        return finalList;
    }

    /**
     * 返回论文实体列表类    按照研究人员名查询论文的作者1的所有的论文
     * @param authorName
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByAuthor1(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor1AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }

    /**
     * 返回论文实体列表类    按照研究人员名查询论文的作者2的所有的论文
     * @param authorName
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByAuthor2(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor2AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }


    /**
     * 返回论文实体列表类    按照研究人员名查询论文的作者3的所有的论文
     * @param authorName
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByAuthor3(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor3AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }


    /**
     *      * 返回论文实体列表类    按照研究人员名查询论文的作者1,2,3的所有的论文
     * @param authorName
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByAuthorName(String authorName){
        UserEntity user = userRepository.findByUserName(authorName);
        return findAllThesisByAuthorId(user.getUserId());
    }


    /**
     * 返回论文实体   按照论文ID查询对应的论文
     * @param thesisId
     * @return
     */
    @Override
    public ThesisEntity findByThesisId(String thesisId){
        return thesisRepository.findByThesisId(thesisId);
    }


    /**
     * 返回论文实体列表类    按照刊名查询所有的论文
     * @param journal
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByJournal(String journal){
        return thesisRepository.findAllByJournal(journal);
    }

    /**
     * 返回论文实体列表类    按照论文标题精确查询所有的论文
     * @param thesisTitle
     * @return
     */
    @Override
    public List<ThesisEntity> findAllThesisByThesisTitleLike(String thesisTitle){
        return thesisRepository.findAllByThesisTitleContaining(thesisTitle);
    }
    //endregion

    //region 著作(BOOK)操作
    @Override
    @Transactional
    public Result addBook(BookEntity bookEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        bookRepository.save(bookEntity);
        return new Result(true, DataCheck.BookCheck.BOOK_ADDED.toString());
    }
    @Override

    @Transactional
    public Result updateBook(BookEntity bookEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        BookEntity b = bookRepository.findByBookId(bookEntity.getBookId());
        b.setBookId(bookEntity.getBookId());
        b.setBookName(bookEntity.getBookName());
        b.setAuthor1(bookEntity.getAuthor1());
        b.setAuthor2(bookEntity.getAuthor2());
        b.setAuthor3(bookEntity.getAuthor3());
        b.setCreativeNature(bookEntity.getCreativeNature());
        b.setBookInformation(bookEntity.getBookInformation());
        b.setBookPublishDate(bookEntity.getBookPublishDate());
        b.setBookPublishStatus(bookEntity.getBookPublishStatus());
        bookRepository.save(b);
        return new Result(true, DataCheck.BookCheck.BOOK_CHANGED.toString());
    }
    @Override
    @Transactional
    public Result deleteBook(BookEntity bookEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        bookEntity.setBookStatus(Const.BookStatus.DELETED);
        bookRepository.save(bookEntity);
        return new Result(true, DataCheck.BookCheck.BOOK_DELETED.toString());
    }
    @Override
    public List<BookEntity> findAllBookByAuthorId(String authorId){
        UserEntity author = userRepository.findByUserId(authorId);
        List<BookEntity> bookListAuthor1 = bookRepository.findAllByAuthor1AndBookStatusIsNot(author, Const.BookStatus.DELETED);
        List<BookEntity> bookListAuthor2 = bookRepository.findAllByAuthor2AndBookStatusIsNot(author, Const.BookStatus.DELETED);
        List<BookEntity> bookListAuthor3 = bookRepository.findAllByAuthor3AndBookStatusIsNot(author, Const.BookStatus.DELETED);
        List<BookEntity> finalList = new ArrayList<>();
        finalList.addAll(bookListAuthor1);
        finalList.addAll(bookListAuthor2);
        finalList.addAll(bookListAuthor3);
        return finalList;
    }
    @Override
    public List<BookEntity> findAllBookByAuthorName(String authorName){

        UserEntity user = userRepository.findByUserName(authorName);
        return findAllBookByAuthorId(user.getUserId());
    }
    @Override
    public List<BookEntity> findAllBookByAuthor1(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return bookRepository.findAllByAuthor1AndBookStatusIsNot(author, Const.BookStatus.DELETED);
    }
    @Override
    public List<BookEntity> findAllBookByAuthor2(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return bookRepository.findAllByAuthor2AndBookStatusIsNot(author, Const.BookStatus.DELETED);
    }
    @Override
    public List<BookEntity> findAllBookByAuthor3(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return bookRepository.findAllByAuthor1AndBookStatusIsNot(author, Const.BookStatus.DELETED);
    }
    @Override
    public BookEntity findByBookId(String bookId){
        return bookRepository.findByBookId(bookId);
    }
    @Override
    public List<BookEntity> findByBookNameLike(String bookName){
        return bookRepository.findByBookNameContaining(bookName);
    }
    //endregion


    //endregion
}