package com.miaoshaproject.service.model;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by kk on 2021/1/18
 */
public class ExcModel {
    private Integer id;

    private String excRequParam;

    private String excName;

    private String excMessage;

    private String operMethod;

    private String operUri;

    private String operIp;

    private DateTime operCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExcRequParam() {
        return excRequParam;
    }

    public void setExcRequParam(String excRequParam) {
        this.excRequParam = excRequParam;
    }

    public String getExcName() {
        return excName;
    }

    public void setExcName(String excName) {
        this.excName = excName;
    }

    public String getExcMessage() {
        return excMessage;
    }

    public void setExcMessage(String excMessage) {
        this.excMessage = excMessage;
    }

    public String getOperMethod() {
        return operMethod;
    }

    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    public String getOperUri() {
        return operUri;
    }

    public void setOperUri(String operUri) {
        this.operUri = operUri;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public DateTime getOperCreateTime() {
        return operCreateTime;
    }

    public void setOperCreateTime(DateTime operCreateTime) {
        this.operCreateTime = operCreateTime;
    }
}
