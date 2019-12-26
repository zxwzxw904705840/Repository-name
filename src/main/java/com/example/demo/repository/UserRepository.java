package com.example.demo.repository;

import com.example.demo.Entity.InstituteEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByUserId(String userId);

    UserEntity findByUserName(String userName);

    List<UserEntity> findByUserNameContaining(String userName);

    List<UserEntity> findUserEntitiesByUserStatus(Const.UserStatus user_status);

    List<UserEntity> findUserEntitiesByCharacters(Const.UserCharacter characters);

    List<UserEntity> findByUserNameLike(String username);

    List<UserEntity> findByUserNameStartingWith(String username);

    List<UserEntity> findByUserNameEndingWith(String username);

    List<UserEntity> findAllByUserNameContainingAndUserStatusIs(String userName, Const.UserStatus ustatus);

    List<UserEntity> findAllByUserNameContainingAndInstituteIs(String userName, InstituteEntity instituteEntity);

    List<UserEntity> findAllByUserNameContainingAndUserStatusIsAndInstituteIs(String userName, Const.UserStatus ustatus, InstituteEntity instituteEntity);

    List<UserEntity> findAllByUserStatusIs(Const.UserStatus status);

    List<UserEntity> findAllByInstituteIs(InstituteEntity instituteEntity);

    List<UserEntity> findAllByUserStatusIsAndInstituteIs(Const.UserStatus ustatus, InstituteEntity instituteEntity);

}