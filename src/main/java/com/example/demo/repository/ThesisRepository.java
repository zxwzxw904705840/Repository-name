package com.example.demo.repository;

import com.example.demo.Entity.ThesisEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<ThesisEntity,String> {
    ThesisEntity findByThesisId(String thesisId);
    List<ThesisEntity> findAllByAuthor1AndStatusIsNot(UserEntity author, Const.ThesisStatus thesisStatus);
    List<ThesisEntity> findAllByAuthor2AndStatusIsNot(UserEntity author, Const.ThesisStatus thesisStatus);
    List<ThesisEntity> findAllByAuthor3AndStatusIsNot(UserEntity author, Const.ThesisStatus thesisStatus);
    List<ThesisEntity> findAllByJournal(String journal);
    List<ThesisEntity> findAllByThesisTitleContaining(String thesisTitle);
}
