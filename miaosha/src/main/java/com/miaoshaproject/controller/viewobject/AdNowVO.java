package com.miaoshaproject.controller.viewobject;

import com.miaoshaproject.service.model.AdModel;

/**
 * Created by kk on 2021/1/20
 */
public class AdNowVO {
    private Integer userid;
    private AdModel adModel;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public AdModel getAdModel() {
        return adModel;
    }

    public void setAdModel(AdModel adModel) {
        this.adModel = adModel;
    }
}
