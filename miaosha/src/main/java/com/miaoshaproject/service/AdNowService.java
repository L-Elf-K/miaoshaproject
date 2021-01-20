package com.miaoshaproject.service;

import com.miaoshaproject.dataobject.AdNowDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.AdNowModel;

import java.util.List;

/**
 * Created by kk on 2021/1/20
 */
public interface AdNowService {

    List<AdNowModel> listAdNow() throws BusinessException;

    void deleteTable();

    void saveAdNow(AdNowModel adNowModel) throws BusinessException;
}
