package com.example.demo.Entity;

import com.example.demo.utils.Const;
import com.example.demo.utils.MD5;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file", schema = "root", catalog = "")
public class FileEntity {
    private String fileId;
    private ProjectEntity project;
    private String filePath;
    private Const.FileStatus fileStatus;

    public FileEntity(){
        this.fileId= MD5.getMD5(String.valueOf(new Date().getTime()));
    }

    public FileEntity(String fileId){this.fileId=fileId;}

    @Id
    @Column(name = "fileId")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @ManyToOne
    @JoinColumn(name="projectId")
    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    @Column(name = "filePath")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fileStatus")
    public Const.FileStatus getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Const.FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }
}
