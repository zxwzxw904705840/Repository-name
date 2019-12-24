package com.example.demo.Entity;

import com.example.demo.utils.Const;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "thesis", schema = "root", catalog = "")
public class ThesisEntity {
    private String thesisId;
    private String thesisTitle;
    private UserEntity author1;
    private UserEntity author2;
    private UserEntity author3;
    private String journal;
    private String volume;
    private String url;
    private Integer pages;
    private Const.ThesisPrivacy privacy;
    private Const.ThesisStatus status;

    public ThesisEntity(){}

    public ThesisEntity(String thesisId){this.thesisId=thesisId;}

    @Id
    @Column(name = "thesisId")
    public String getThesisId() {
        return thesisId;
    }

    public void setThesisId(String thesisId) {
        this.thesisId = thesisId;
    }

    @Column(name = "thesisTitle")
    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
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

    @Column(name = "journal")
    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    @Column(name = "volume")
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "pages")
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "privacy")
    public Const.ThesisPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Const.ThesisPrivacy privacy) {
        this.privacy = privacy;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    public Const.ThesisStatus getStatus() {
        return status;
    }

    public void setStatus(Const.ThesisStatus status) {
        this.status = status;
    }

    @ManyToMany(mappedBy = "thesis")
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    private Set<ProjectEntity> projects;
}
