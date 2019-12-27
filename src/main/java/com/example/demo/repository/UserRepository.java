package com.example.demo.repository;

import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity> {
   // List<UserEntity> findAllBy(Specification<UserEntity> spec);
    
    ArrayList<UserEntity> findByUserStatusNot(Const.UserStatus userStatus);


    ArrayList<UserEntity> findAllByUserIdLike(String userId);

    ArrayList<UserEntity> findAllByUserNameLike(String userName);

    UserEntity findByUserId(String userId);

    UserEntity findByUserName(String userName);

    UserEntity findByUserNameContaining(String userName);

    List<UserEntity> findUserEntitiesByUserStatus(Const.UserStatus user_status);
    

    List<UserEntity> findByUserNameLike(String username);

    List<UserEntity> findByUserNameStartingWith(String username);

    List<UserEntity> findByUserNameEndingWith(String username);

    List<UserEntity> findByUserNameContainingOrInstituteContainingOrUserStatusContaining(String username,String instituteid, Const.UserStatus status);

    List<UserEntity> findUserEntitiesByCharacters(Const.UserCharacter role);



}