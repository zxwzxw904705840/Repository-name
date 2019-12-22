package com.example.demo.utils;

public class DataCheck {
    public enum UserDataCheck{
        USER_NOT_EXISTS, USER_ID_IS_EMPTY, USER_ID_IS_TOO_LONG, PASSWORD_IS_EMPTY,
        PASSWORD_TOO_SHORT,PASSWORD_TOO_LONG,USER_ROLE_ERROR,USERNAME_IS_EMPTY,
        PHONE_IS_EMPTY,ILLEGAL_TELEPHONE,ACCOUNT_CAN_USE,PERMISSION_DENIED,
        EMPTY_TITLE, USER_ID_EXISTS, USER_STATUS_UPDATED, USER_DELETED, USER_NOT_EXISTS_OR_STATUS_NOT_NORMAL,
        USERS_DELETED, IS_ADMIN, IS_RESEARCHER
    }
    public enum UserDataSet{
        USERNAME_CHANGED, INSTITUTE_CHANGED, USER_TITLE_CHANGED, USER_EMAIL_CHANGED, USER_PASSWORD_CHANGED,
        USER_PHONE_CHANGED,USER_DELETED,USER_STATUS_CHANGED
    }

    public enum InstituteCheck{
        INSTITUTE_EXISTS, INSTITUTE_NOT_EXISTS, INSTITUTE_ID_IS_EMPTY,INSTITUTE_NAME_IS_EMPTY,
        INSTITUTE_CAN_USE
    }
}
