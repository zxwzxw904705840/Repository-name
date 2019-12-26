package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.utils.Const;
import com.example.demo.utils.Result;

import java.util.List;

public interface UserService {
    List<UserEntity> getUserList();
    Result getUserById(String userId);
    List<UserEntity> getUsersById(List<String> userId);
    List<UserEntity> getUsersByStatus(Const.UserStatus status, String adminId);
    List<UserEntity> getUsersByRole(Const.UserCharacter role);
    UserEntity getUserByName(String userName);

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

    List<UserEntity> findByUserNameLike(String userName);

    List<UserEntity> findByUserNameStartingWith(String userName);

    List<UserEntity> findByUserNameEndingWith(String userName);

    List<UserEntity> findByUserNameInstituteStatus(String userName,String institute_id,Const.UserStatus user_status);

    //region 研究员操作集

    //region 我的论文操作
    Result addThesis(ThesisEntity thesisEntity, UserEntity userEntity);
    Result updateThesis(ThesisEntity thesisEntity, UserEntity userEntity);
    Result deleteThesis(ThesisEntity thesisEntity, UserEntity userEntity);
    List<ThesisEntity> findAllThesisByAuthorId(String authorId);
    List<ThesisEntity> findAllThesisByAuthor1(String authorName);
    List<ThesisEntity> findAllThesisByAuthor2(String authorName);
    List<ThesisEntity> findAllThesisByAuthor3(String authorName);
    List<ThesisEntity> findAllThesisByAuthorName(String authorName);
    ThesisEntity findByThesisId(String thesisId);
    List<ThesisEntity> findAllThesisByJournal(String journal);
    List<ThesisEntity> findAllThesisByThesisTitleLike(String thesisTitle);
    //endregion

    //region 我的著作操作
    Result addBook(BookEntity bookEntity, UserEntity userEntity);
    Result updateBook(BookEntity bookEntity, UserEntity userEntity);
    Result deleteBook(BookEntity bookEntity, UserEntity userEntity);
    List<BookEntity> findAllBookByAuthorId(String authorId);
    List<BookEntity> findAllBookByAuthorName(String authorName);
    List<BookEntity> findAllBookByAuthor1(String authorName);
    List<BookEntity> findAllBookByAuthor2(String authorName);
    List<BookEntity> findAllBookByAuthor3(String authorName);
    BookEntity findByBookId(String bookId);
    List<BookEntity> findByBookNameLike(String bookName);
    //endregion

    //endregion


}
