package com.example.demo.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Result {
    private boolean success;
    private String message;
    private HashMap<String,Object> objectMap;
    private long timestamp;

    public Result(boolean success, String message){
        this.success=success;
        this.message=message;
        this.objectMap=new HashMap();
        this.timestamp=new Date().getTime();
    }
    public boolean isSuccess(){
        return this.success;
    }
    public String getMessage(){
        return this.message;
    }
    public boolean hasObject(){
        return this.objectMap.isEmpty();
    }
    public void addObject(String key,Object value){
        objectMap.put(key,value);
    }
    public Object getObject(String key){
        return objectMap.get(key);
    }
    public long  getTimestamp(){return this.timestamp;}

    @Override
    public String toString(){
        return this.success + " " + this.message;
    }
}
