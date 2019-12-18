package com.example.demo.Entity;

import com.example.demo.utils.MD5;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "institute", schema = "root", catalog = "")
public class InstituteEntity {

    private String instituteId;
    private String instituteName;

    public InstituteEntity(){
        this.instituteId= MD5.getMD5(String.valueOf(new Date().getTime()));
    }

    public InstituteEntity(String instituteId) {
        this.instituteId = instituteId;
    }

    public InstituteEntity(String instituteId, String instituteName) {
        this.instituteId = instituteId;
        this.instituteName = instituteName;
    }

    @Id
    @Column(name = "instituteId")
    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    @Column(name = "instituteName")
    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}
