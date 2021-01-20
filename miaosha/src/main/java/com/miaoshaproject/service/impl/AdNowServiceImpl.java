package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.AdNowDOMapper;
import com.miaoshaproject.dataobject.AdNowDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.AdNowService;
import com.miaoshaproject.service.model.AdModel;
import com.miaoshaproject.service.model.AdNowModel;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/20
 */
@Service
public class AdNowServiceImpl implements AdNowService {
    @Resource
    AdNowDOMapper adNowDOMapper;

    @Override
    public List<AdNowModel> listAdNow() throws BusinessException {
        List<AdNowDO> adNowDOList = adNowDOMapper.selectAll();
        if (adNowDOList == null) throw new BusinessException(EmBusinessError.AD_BLANK_ERROR);
        List<AdNowModel> adNowModelList = adNowDOList.stream().map(adNowDO -> {
            AdNowModel adNowModel = this.convertAdNowModelFromAdNowDO(adNowDO);
            return adNowModel;
        }).collect(Collectors.toList());
        return adNowModelList;
    }

    @Override
    public void deleteTable() {
        adNowDOMapper.deleteTable();
    }

    @Override
    public void saveAdNow(AdNowModel adNowModel) throws BusinessException {
        if (adNowModel == null) throw new BusinessException(EmBusinessError.AD_BLANK_ERROR);
        AdNowDO adNowDO = this.convertAdNowDOFromAdNowModel(adNowModel);
        adNowDOMapper.insertSelective(adNowDO);
    }

    private AdNowDO convertAdNowDOFromAdNowModel(AdNowModel adNowModel) {
        AdNowDO adNowDO = new AdNowDO();
        BeanUtils.copyProperties(adNowModel, adNowDO);
        return adNowDO;
    }

    private AdNowModel convertAdNowModelFromAdNowDO(AdNowDO adNowDO) {
        if (adNowDO == null) return null;
        AdNowModel adNowModel = new AdNowModel();
        BeanUtils.copyProperties(adNowDO, adNowModel);
        return adNowModel;
    }
}
