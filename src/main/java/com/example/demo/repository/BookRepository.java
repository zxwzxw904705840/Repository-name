package com.example.demo.repository;

import com.example.demo.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,String> {
    List<BookEntity> findAllByAuthor1Containing(String authorId);
    List<BookEntity> findAllByAuthor2Containing(String authorId);
    List<BookEntity> findAllByAuthor3Containing(String authorId);
}
