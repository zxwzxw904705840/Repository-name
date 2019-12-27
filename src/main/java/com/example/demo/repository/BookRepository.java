package com.example.demo.repository;

import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.utils.Const;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,String> {
    List<BookEntity> findAllByAuthor1AndBookStatusIsNot(UserEntity author, Const.BookStatus bookStatus);
    List<BookEntity> findAllByAuthor2AndBookStatusIsNot(UserEntity author, Const.BookStatus bookStatus);
    List<BookEntity> findAllByAuthor3AndBookStatusIsNot(UserEntity author, Const.BookStatus bookStatus);
    BookEntity findByBookId(String bookId);
    List<BookEntity> findByBookNameContaining(String bookName);

}
