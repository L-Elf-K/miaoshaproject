package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ExcModel;

import java.util.Date;
import java.util.List;

/**
 * Created by kk on 2021/1/18
 */
public interface ExcService {

    void saveExc(ExcModel excModel);

    List<ExcModel> queryExcByTime(Date dateTime) throws BusinessException;

}
