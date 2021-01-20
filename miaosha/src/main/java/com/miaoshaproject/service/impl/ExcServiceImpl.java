package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.ExcDOMapper;
import com.miaoshaproject.dataobject.ExcDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ExcService;
import com.miaoshaproject.service.model.ExcModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/18
 */
@Service
public class ExcServiceImpl implements ExcService {
    @Resource
    ExcDOMapper excDOMapper;

    @Override
    public void saveExc(ExcModel excModel) {
        ExcDO excDO = this.convertExcDOFromExcModel(excModel);
        excDOMapper.insertSelective(excDO);
    }

    private ExcDO convertExcDOFromExcModel(ExcModel excModel) {
        ExcDO excDO = new ExcDO();
        BeanUtils.copyProperties(excModel, excDO);
        excDO.setOperCreateTime(excModel.getOperCreateTime().toDate());
        return excDO;
    }

    @Override
    public List<ExcModel> queryExcByTime(Date dateTime) throws BusinessException {
        List<ExcDO> excDOList = excDOMapper.selectByTime(dateTime);
        if (excDOList == null) throw new BusinessException(EmBusinessError.EXC_QUERY_ERROR);
        List<ExcModel> excModelList = excDOList.stream().map(excDO -> {
            ExcModel excModel = this.convertExcModelFromExcDO(excDO);
            return excModel;
        }).collect(Collectors.toList());
        return excModelList;
    }

    private ExcModel convertExcModelFromExcDO(ExcDO excDO) {
        ExcModel excModel = new ExcModel();
        BeanUtils.copyProperties(excDO, excModel);
        excModel.setOperCreateTime(new DateTime(excDO.getOperCreateTime()));
        return excModel;
    }
}
