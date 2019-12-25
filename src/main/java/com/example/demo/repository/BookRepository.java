package com.example.demo.repository;

import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,String> {
    List<BookEntity> findAllByAuthor1(UserEntity author);
    List<BookEntity> findAllByAuthor2(UserEntity author);
    List<BookEntity> findAllByAuthor3(UserEntity author);
    BookEntity findByBookId(String bookId);
    List<BookEntity> findByBookNameContaining(String bookName);

}
