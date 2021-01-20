package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.AdDOMapper;
import com.miaoshaproject.dataobject.AdDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.model.AdModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/2
 */
@Service
public class AdServiceImpl implements AdService {

    @Resource
    AdDOMapper adDOMapper;

    @Override
    public AdModel getAd(Integer id) throws BusinessException {
        AdDO adDO = adDOMapper.selectByPrimaryKey(id);
        if (adDO == null) throw new BusinessException(EmBusinessError.AD_BLANK_ERROR);
        AdModel adModel = this.convertAdModelFromAdDO(adDO);
        return adModel;
    }

    @Override
    public List<AdModel> getRelatedAd(String category) throws BusinessException {
        List<AdDO> adDOList = adDOMapper.select7AdByCategory(category);
        if (adDOList == null) throw new BusinessException(EmBusinessError.AD_BLANK_ERROR);
        List<AdModel> adModelList = adDOList.stream().map(adDO -> {
            AdModel adModel= this.convertAdModelFromAdDO(adDO);
            return adModel;
        }).collect(Collectors.toList());
        return adModelList;
    }

    @Override
    public AdModel select1AdIdByRand() throws BusinessException {
        AdDO adDO = adDOMapper.select1AdByRand();
        if (adDO == null) throw new BusinessException(EmBusinessError.AD_BLANK_ERROR);
        AdModel adModel = this.convertAdModelFromAdDO(adDO);
        return adModel;
    }

    private  AdModel convertAdModelFromAdDO(AdDO adDO) {
        if (adDO == null) {
            return null;
        }
        AdModel adModel = new AdModel();
        BeanUtils.copyProperties(adDO, adModel);
        adModel.setPrice(new BigDecimal(adDO.getPrice()));
        return adModel;
    }
}
