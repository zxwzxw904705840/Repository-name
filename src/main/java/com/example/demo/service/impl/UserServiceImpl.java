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

    //region User部分
    //region 获得用户
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
    public UserEntity getUserByName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<UserEntity> getUsersById(List<String> userId){
        return userRepository.findAllById(userId);
    }

    @Override
    public List<UserEntity> getUsersByStatus(Const.UserStatus status, String adminId){
        UserEntity adminEntity = userRepository.findByUserId(adminId);
        Result result = checkUserPermission(adminEntity);
        if(!result.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return null;
        }
        return  userRepository.findUserEntitiesByUserStatus(status);
    }

    public List<UserEntity> getUsersByRole(Const.UserCharacter role){
        return userRepository.findUserEntitiesByCharacters(role);
    }
    //endregion

    //region 添加新用户
    @Override
    public Result addUser(UserEntity user){
        Result result = checkUser(user);
        if(!result.isSuccess()){
            return result;
        }
        user.setUserStatus(Const.UserStatus.REVIEWING);
        userRepository.save(user);
        return new Result(true, "新用户添加了！");
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
        if(!adminresult.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return adminresult;
        }
        userRepository.delete(userEntity);
        return new Result(true,DataCheck.UserDataSet.USER_DELETED.toString());
    }

    @Override
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
        if(adminresult.getMessage().equals(DataCheck.UserDataCheck.IS_ADMIN.toString())){
            return adminresult;
        }
        if(status != Const.UserStatus.DELETED && status != Const.UserStatus.NORMAL && status != Const.UserStatus.REVIEWING){
            return new Result(false, "用户状态不存在");
        }
        userEntity.setUserStatus(status);
        return new Result(true, DataCheck.UserDataSet.USER_STATUS_CHANGED.toString());

    }

    public Result AgreeUserUpdate(String userid, String adminid){
        UserEntity user = userRepository.findByUserId(userid);
        UserEntity admin = userRepository.findByUserId(adminid);
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

    public List<UserEntity> findByUserNameInstituteStatus(String username,String instituteid, Const.UserStatus status){
        return userRepository.findByUserNameContainingOrInstituteContainingOrUserStatusContaining(username, instituteid, status);
    }
    //endregion
    //endregion

    //region 研究员操作集

    //region 我的论文操作
    @Transactional
    @Override
    public Result addThesis(ThesisEntity thesisEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        thesisRepository.save(thesisEntity);
        return new Result(true, DataCheck.ThesisCheck.THESIS_ADDED.toString());
    }
    @Transactional
    @Override
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
    @Override
    @Transactional
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
    @Override
    public List<ThesisEntity> findAllThesisByAuthor1(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor1AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }
    @Override
    public List<ThesisEntity> findAllThesisByAuthor2(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor2AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }
    @Override
    public List<ThesisEntity> findAllThesisByAuthor3(String authorName){
        UserEntity author = userRepository.findByUserName(authorName);
        return thesisRepository.findAllByAuthor3AndStatusIsNot(author, Const.ThesisStatus.DELETED);
    }
    @Override
    public List<ThesisEntity> findAllThesisByAuthorName(String authorName){
        UserEntity user = userRepository.findByUserName(authorName);
        return findAllThesisByAuthorId(user.getUserId());
    }
    @Override
    public ThesisEntity findByThesisId(String thesisId){
        return thesisRepository.findByThesisId(thesisId);
    }
    @Override
    public List<ThesisEntity> findAllThesisByJournal(String journal){
        return thesisRepository.findAllByJournal(journal);
    }
    @Override
    public List<ThesisEntity> findAllThesisByThesisTitleLike(String thesisTitle){
        return thesisRepository.findAllByThesisTitleContaining(thesisTitle);
    }
    //endregion

    //region 我的著作操作
    @Override
    public Result addBook(BookEntity bookEntity, UserEntity researcher){
        Result result = checkUserPermission(researcher);
        if(!result.isSuccess()){
            return result;
        }
        bookRepository.save(bookEntity);
        return new Result(true, DataCheck.BookCheck.BOOK_ADDED.toString());
    }
    @Override
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
        UserEntity user = userRepository.findByUserNameContaining(authorName);
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