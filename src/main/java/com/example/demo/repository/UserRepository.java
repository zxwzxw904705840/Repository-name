package com.example.demo.repository;

import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByUserId(String userId);
    ArrayList<UserEntity> findUserEntitiesByUserStatus(Const.UserStatus status);
}
