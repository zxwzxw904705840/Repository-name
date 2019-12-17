package com.example.demo.utils;

import java.util.Date;

public class Result<T> {
    private boolean success;
    private String message;
    private T object;
    private long timestamp;

    public Result(boolean success, String message, T object){
        this.success=success;
        this.message=message;
        this.object=object;
        this.timestamp=new Date().getTime();
    }
    public Result(boolean success, String message){
        this.success=success;
        this.message=message;
        this.object=null;
        this.timestamp=new Date().getTime();
    }
    public boolean isSuccess(){
        return this.success;
    }
    public String getMessage(){
        return this.message;
    }
    public T getObject(){
        return this.object;
    }
    public long  getTimestamp(){return this.timestamp;}
    public boolean hasObject(){
        if(object==null){
            return false;
        }
        return success;
    }

    @Override
    public String toString(){
        return this.success + " " + this.message + " "+ object;
    }
}
