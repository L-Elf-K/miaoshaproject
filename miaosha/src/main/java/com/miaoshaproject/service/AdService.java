package com.miaoshaproject.service;

import com.miaoshaproject.dataobject.AdDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.AdModel;

import java.util.List;

/**
 * Created by kk on 2021/1/2
 */
public interface AdService {

    //获取广告
    AdModel getAd(Integer id) throws BusinessException;

    List<AdModel> getRelatedAd(String category) throws BusinessException;

    AdModel select1AdIdByRand() throws BusinessException;
}
