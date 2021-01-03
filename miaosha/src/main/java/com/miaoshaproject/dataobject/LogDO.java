package com.miaoshaproject.dataobject;

import java.util.Date;

public class LogDO {
    private Integer id;

    private Date logTime;

    private String modelName;

    private String modelFunc;

    private String level;

    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getModelFunc() {
        return modelFunc;
    }

    public void setModelFunc(String modelFunc) {
        this.modelFunc = modelFunc == null ? null : modelFunc.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}