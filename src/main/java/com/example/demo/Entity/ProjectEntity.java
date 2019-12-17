package com.example.demo.Entity;

import com.example.demo.utils.Const;
import com.example.demo.utils.MD5;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "project", schema = "root", catalog = "")
public class ProjectEntity {

    private String projectId;
    private String projectCode;
    private String projectName;
    private Const.ProjectType projectType;
    private UserEntity projectManager;
    private Const.ProjectLevel projectLevel;
    private Const.ProjectProgress projectProcess;
    private String projectSource;
    private Date projectEstablishDate;
    private Date projectPlannedDate;
    private Date projectLaunchDate;
    private Date projectFinishDate;
    private Integer projectFund;
    private Const.ProjectSourceType projectSourceType;
    private Const.ProjectResearchType projectResearchType;
    private Set<UserEntity>members;

    public ProjectEntity(){
        this.projectId=MD5.getMD5(String.valueOf(new Date().getTime()));
    }

    @Id
    @Column(name = "projectId")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name = "projectCode")
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Column(name = "projectName")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "projectType")
    public Const.ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(Const.ProjectType projectType) {
        this.projectType = projectType;
    }

    @ManyToOne
    @JoinColumn(name="projectManagerId")
    public UserEntity getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserEntity projectManager) {
        this.projectManager = projectManager;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "projectLevel")
    public Const.ProjectLevel getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(Const.ProjectLevel projectLevel) {
        this.projectLevel = projectLevel;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "projectProcess")
    public Const.ProjectProgress getProjectProcess() {
        return projectProcess;
    }

    public void setProjectProcess(Const.ProjectProgress projectProcess) {
        this.projectProcess = projectProcess;
    }

    @Column(name = "projectSource")
    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "projectEstablishDate")
    public Date getProjectEstablishDate() {
        return projectEstablishDate;
    }

    public void setProjectEstablishDate(Date projectEstablishDate) {
        this.projectEstablishDate = projectEstablishDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "projectPlannedDate")
    public Date getProjectPlannedDate() {
        return projectPlannedDate;
    }

    public void setProjectPlannedDate(Date projectPlannedDate) {
        this.projectPlannedDate = projectPlannedDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "projectLaunchDate")
    public Date getProjectLaunchDate() {
        return projectLaunchDate;
    }

    public void setProjectLaunchDate(Date projectLaunchDate) {
        this.projectLaunchDate = projectLaunchDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "projectFinishDate")
    public Date getProjectFinishDate() {
        return projectFinishDate;
    }

    public void setProjectFinishDate(Date projectFinishDate) {
        this.projectFinishDate = projectFinishDate;
    }

    @Column(name = "projectFund")
    public Integer getProjectFund() {
        return projectFund;
    }

    public void setProjectFund(Integer projectFund) {
        this.projectFund = projectFund;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "projectSourceType")
    public Const.ProjectSourceType getProjectSourceType() {
        return projectSourceType;
    }

    public void setProjectSourceType(Const.ProjectSourceType projectSourceType) {
        this.projectSourceType = projectSourceType;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "projectResearchType")
    public Const.ProjectResearchType getProjectResearchType() {
        return projectResearchType;
    }

    public void setProjectResearchType(Const.ProjectResearchType projectResearchType) {
        this.projectResearchType = projectResearchType;
    }

    @ManyToMany
    @JoinTable(name="r_project_user",joinColumns={@JoinColumn(name="projectId")},inverseJoinColumns={@JoinColumn(name="userId")})
    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }
}
