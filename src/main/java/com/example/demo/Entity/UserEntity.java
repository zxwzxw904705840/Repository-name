package com.example.demo.Entity;

import com.example.demo.utils.Const;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user", schema = "root", catalog = "")
public class UserEntity {

    private String userId;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private Const.UserTitle title;
    private InstituteEntity institute;
    private Const.UserCharacter characters;
    private Const.UserStatus userStatus;
    private Set<ProjectEntity> projects;

    public UserEntity(){}

    public UserEntity(String userId){this.userId = userId;}

    public UserEntity(String userId,Const.UserStatus userStatus){
        this.userStatus = userStatus;
        this.userId = userId;
    }

    @Id
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "title")
    public Const.UserTitle getTitle() {
        return title;
    }

    public void setTitle(Const.UserTitle title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name="instituteId")
    public InstituteEntity getInstitute() {
        return institute;
    }

    public void setInstitute(InstituteEntity institute) {
        this.institute = institute;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "characters")
    public Const.UserCharacter getCharacters() {
        return characters;
    }

    public void setCharacters(Const.UserCharacter characters) {
        this.characters = characters;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "userStatus")
    public Const.UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Const.UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @ManyToMany(mappedBy = "members")
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
