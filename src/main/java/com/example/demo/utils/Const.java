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
    public enum ProjectProgress{ //项目进度
        OPEN_QUESTION,IN_PROGRESS,COMPLETE
    }
    public enum ProjectSourceType{ //项目来源类别
        ENTERPRISE,GOVERNMENT,SCHOOL,COMMUNITY,PERSONAL
    }
    public enum ProjectResearchType{ //项目研究类别
        BASIC_RESEARCH,APPLIED_RESEARCH,TECHNOLOGY_DEVELOPMENT,OTHER
    }
    public enum ThesisPrivacy{ //论文私密性
        PUBLIC,PRIVATE
    }
    public enum ThesisStatus{ //论文状态
        NORMAL,REVIEWING,DELETED
    }
    public enum BookCreativeNature{ //著作创作性质
        ORIGINAL,ADAPTATION,TRANSLATION,COMPILATION,COMMENTARY,ARRANGE,OTHER
    }
    public enum BookPublishStatus{ //著作发表状态
        PUBLISHED,UNPUBLISHED
    }
    public enum BookStatus{ //著作状态
        NORMAL,REVIEWING,DELETED
    }
    public enum FileStatus{ //著作状态
        NORMAL,DELETED
    }
}
