package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.AdDOMapper;
import com.miaoshaproject.dataobject.AdDO;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.model.AdModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by kk on 2021/1/2
 */
@Service
public class AdServiceImpl implements AdService {

    @Resource
    AdDOMapper adDOMapper;

    @Override
    public AdModel getAd(Integer id) {
        AdModel adModel = new AdModel();
        AdDO adDO = new AdDO();
        adDO = adDOMapper.selectByPrimaryKey(id);
        adModel = this.convertAdModelFromAdDO(adDO);
        return adModel;
    }
    private  AdModel convertAdModelFromAdDO(AdDO adDO) {
        if (adDO == null) {
            return null;
        }
        AdModel adModel = new AdModel();
        BeanUtils.copyProperties(adDO, adModel);
        return adModel;
    }
}
