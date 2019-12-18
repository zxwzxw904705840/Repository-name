package com.example.demo.Entity;

import com.example.demo.utils.Const;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book", schema = "root", catalog = "")
public class BookEntity {
    private String bookId;
    private String bookName;
    private UserEntity author1;
    private UserEntity author2;
    private UserEntity author3;
    private Const.BookCreativeNature creativeNature;
    private String bookInformation;
    private Date bookPublishDate;
    private Const.BookPublishStatus bookPublishStatus;
    private Const.BookStatus bookStatus;

    @Id
    @Column(name = "bookId")
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Column(name = "bookName")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @ManyToOne
    @JoinColumn(name="author1Id")
    public UserEntity getAuthor1() {
        return author1;
    }

    public void setAuthor1(UserEntity author1) {
        this.author1 = author1;
    }

    @ManyToOne
    @JoinColumn(name="author2Id")
    public UserEntity getAuthor2() {
        return author2;
    }

    public void setAuthor2(UserEntity author2) {
        this.author2 = author2;
    }

    @ManyToOne
    @JoinColumn(name="author3Id")
    public UserEntity getAuthor3() {
        return author3;
    }

    public void setAuthor3(UserEntity author3) {
        this.author3 = author3;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "creativeNature")
    public Const.BookCreativeNature getCreativeNature() {
        return creativeNature;
    }

    public void setCreativeNature(Const.BookCreativeNature creativeNature) {
        this.creativeNature = creativeNature;
    }

    @Column(name = "bookInformation")
    public String getBookInformation() {
        return bookInformation;
    }

    public void setBookInformation(String bookInformation) {
        this.bookInformation = bookInformation;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "bookPublishDate")
    public Date getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(Date bookPublishDate) {
        this.bookPublishDate = bookPublishDate;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "bookPublishStatus")
    public Const.BookPublishStatus getBookPublishStatus() {
        return bookPublishStatus;
    }

    public void setBookPublishStatus(Const.BookPublishStatus bookPublishStatus) {
        this.bookPublishStatus = bookPublishStatus;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "bookStatus")
    public Const.BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Const.BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}
