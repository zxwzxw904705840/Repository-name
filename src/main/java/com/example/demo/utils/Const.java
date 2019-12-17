package com.example.demo.utils;

public final class Const {
    public enum UserTitle { //用户职称
        LECTURER,ASSOCIATE_PROFESSOR,PROFESSOR,ASSOCIATE_RESEARCHER,RESEARCHER
    }
    public enum UserCharacter{ //用户角色
        ADMINISTRATION,TEACHER,NORMAL_USER
    }
    public enum UserStatus { //用户状态
        NORMAL,REVIEWING,DELETED
    }
    public enum ProjectType{ //项目类型
        INNOVATION,ENTREPRENEUR
    }
    public enum ProjectLevel{ //项目等级
        SCHOOL,CITY,PROVINCIAL,NATIONAL,OTHER
    }
    public enum ProjectProgress{ //项目等级
        OPEN_QUESTION,IN_PROGRESS,COMPLETE
    }
    public enum ProjectSourceType{ //项目来源类别
        ENTERPRISE,GOVERNMENT,SCHOOL,COMMUNITY,PERSONAL
    }
    public enum ProjectResearchType{ //项目来源类别
        BASIC_RESEARCH,APPLIED_RESEARCH,TECHNOLOGY_DEVELOPMENT,OTHER
    }

}
