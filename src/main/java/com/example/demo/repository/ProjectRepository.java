package com.example.demo.repository;

import com.example.demo.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,String> {
    ArrayList<ProjectEntity> findAll();
    ArrayList<ProjectEntity> findAllByProAndProjectNameLike(String projectName);
    ProjectEntity findByProjectId(String projectId);
}
