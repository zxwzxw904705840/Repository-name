package com.example.demo.repository;

import com.example.demo.Entity.ThesisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<ThesisEntity,String> {
    List<ThesisEntity> findAllByAuthor1ContainingOrAndAuthor2ContainingOrAndAuthor3Containing(String userId);
}
