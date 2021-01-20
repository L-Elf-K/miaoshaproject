package com.miaoshaproject.controller.viewobject;

import com.miaoshaproject.controller.Emvippower.Emvippower;

/**
 * Created by kk on 2021/1/2
 */
public class VipVO {
    private Integer id;
    private Integer userId;
    private Emvippower level;
    private String[] power;
    private String startDate;
    private String endDate;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Emvippower getLevel() {
        return level;
    }

    public void setLevel(Emvippower level) {
        this.level = level;
    }

    public String[] getPower() {
        return power;
    }

    public void setPower(String[] power) {
        this.power = power;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
