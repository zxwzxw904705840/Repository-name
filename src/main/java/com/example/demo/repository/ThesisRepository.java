package com.example.demo.repository;

import com.example.demo.Entity.ThesisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisRepository extends JpaRepository<ThesisEntity,String> {
}
