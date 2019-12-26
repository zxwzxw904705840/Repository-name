package com.example.demo.repository;

import com.example.demo.Entity.InstituteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface InstituteRepository extends JpaRepository<InstituteEntity,String> {
    InstituteEntity findInstituteEntityByInstituteId(String instituteId);
    ArrayList<InstituteEntity> findAll();
}
