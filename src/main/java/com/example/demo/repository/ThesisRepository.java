package com.example.demo.repository;

import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<ThesisEntity,String> {
    ThesisEntity findByThesisId(String thesisId);
    List<ThesisEntity> findAllByAuthor1(UserEntity author);
    List<ThesisEntity> findAllByAuthor2(UserEntity author);
    List<ThesisEntity> findAllByAuthor3(UserEntity author);
    List<ThesisEntity> findAllByJournal(String journal);
    List<ThesisEntity> findAllByThesisTitleLike(String thesisTitle);
}
