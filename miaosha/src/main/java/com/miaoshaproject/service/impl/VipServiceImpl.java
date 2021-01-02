package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.VipDOMapper;
import com.miaoshaproject.dataobject.VipDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.VipServise;
import com.miaoshaproject.service.model.VipModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/1
 */
@Service
public class VipServiceImpl implements VipServise {

    @Resource
    VipDOMapper vipDOMapper;

    @Resource
    UserDOMapper userDOMapper;

    @Autowired
    ValidatorImpl validator;

    @Override
    public int deleteVip(Integer userId) {
        return vipDOMapper.deleteByUserId(userId);
    }

    @Override
    public VipModel createVip(VipModel vipModel) throws BusinessException {
        ValidationResult result = validator.validate(vipModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //转化vipmodel -> dataobject
        VipDO vipDO = this.covertVipDOFromVipModel(vipModel);

        //写入数据库
        vipDOMapper.insertSelective(vipDO);

        return vipModel;
    }

    @Override
    public List<VipModel> listVip() {
        List<VipDO> vipDOList = vipDOMapper.listVip();
        List<VipModel> vipModelList = vipDOList.stream().map(vipDO -> {
            VipModel vipModel = this.covertFromVipDO(vipDO);
            return vipModel;
        }).collect(Collectors.toList());
        return vipModelList;
    }

    private VipModel covertFromVipDO(VipDO vipDO) {
        if (vipDO == null) return null;
        VipModel vipModel = new VipModel();
        BeanUtils.copyProperties(vipDO, vipModel);
        vipModel.setStartDate(new DateTime(vipDO.getStartDate()));
        vipModel.setEndDate(new DateTime(vipDO.getEndDate()));
        return vipModel;
    }

    private VipDO covertVipDOFromVipModel(VipModel vipModel) {
        if (vipModel == null) {
            return null;
        }
        VipDO vipDO = new VipDO();
        BeanUtils.copyProperties(vipModel, vipDO);
        return vipDO;
    }
}
