package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.VipDOMapper;
import com.miaoshaproject.dao.VipRenewDOMapper;
import com.miaoshaproject.dataobject.VipDO;
import com.miaoshaproject.dataobject.VipRenewDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.VipServise;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.service.model.VipModel;
import com.miaoshaproject.service.model.VipRenewModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    VipRenewDOMapper vipRenewDOMapper;

    @Autowired
    UserService userService;

    @Autowired
    ValidatorImpl validator;

    @Override
    public int deleteVip(Integer id) {
        return vipDOMapper.deleteByPrimaryKey(id);
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
        try {
            vipDOMapper.insertSelective(vipDO);
        }catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户已是VIP，只可以续费");
        }

        return vipModel;
    }

    @Override
    public VipModel getVip(Integer id) throws BusinessException {
        VipDO vipDO = vipDOMapper.selectByPrimaryKey(id);
        if (vipDO == null) throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"查无此vip");
        VipModel vipModel = this.covertFromVipDO(vipDO);
        return vipModel;
    }

    @Override
    @Transactional
    public int updateVipDuration(VipModel vipModel) {
        VipDO vipDO= this.covertVipDOFromVipModel(vipModel);
        return vipDOMapper.updateByPrimaryKeySelective(vipDO);
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

    @Override
    public List<VipModel> listVipByLevel() {
        List<VipDO> vipDOList = vipDOMapper.listVipByLevel();
        List<VipModel> vipModelList = vipDOList.stream().map(vipDO -> {
            VipModel vipModel = this.covertFromVipDO(vipDO);
            return vipModel;
        }).collect(Collectors.toList());
        return vipModelList;
    }

    @Override
    public List<VipRenewModel> listVipRenew() {
        List<VipRenewDO> vipRenewDOList = vipRenewDOMapper.listVipRenew();

        List<VipRenewModel> vipRenewModelList = vipRenewDOList.stream().map(vipRenewDO -> {
            VipRenewModel vipRenewModel;
            vipRenewModel = this.convertVipRenewModelFromVipRenewDO(vipRenewDO);
            return vipRenewModel;
        }).collect(Collectors.toList());
        return vipRenewModelList;
    }

    @Override
    @Transactional
    public VipRenewModel addVipRenew(VipRenewModel vipRenewModel) throws BusinessException {
        if (vipRenewModel == null) throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        VipRenewDO vipRenewDO = this.convertVipRenewDOFromVipRenewModel(vipRenewModel);
        vipRenewDOMapper.insertSelective(vipRenewDO);
        return vipRenewModel;
    }

    private VipModel covertFromVipDO(VipDO vipDO) {
        if (vipDO == null) return null;
        VipModel vipModel = new VipModel();
        BeanUtils.copyProperties(vipDO, vipModel);
        vipModel.setStartDate(new DateTime(vipDO.getStartDate()));
        vipModel.setEndDate(new DateTime(vipDO.getEndDate()));
        UserModel userModel = userService.getUserById(vipDO.getUserId());
        vipModel.setUserModel(userModel);
        return vipModel;
    }
    private VipRenewDO convertVipRenewDOFromVipRenewModel(VipRenewModel vipRenewModel) {
        if (vipRenewModel == null) return null;
        VipRenewDO vipRenewDO = new VipRenewDO();
        BeanUtils.copyProperties(vipRenewModel, vipRenewDO);
        return vipRenewDO;
    }
    private VipDO covertVipDOFromVipModel(VipModel vipModel) {
        if (vipModel == null) {
            return null;
        }
        VipDO vipDO = new VipDO();
        BeanUtils.copyProperties(vipModel, vipDO);
        vipDO.setStartDate(vipModel.getStartDate().toDate());
        vipDO.setEndDate(vipModel.getEndDate().toDate());
        return vipDO;
    }
    private VipRenewModel convertVipRenewModelFromVipRenewDO(VipRenewDO vipRenewDO) {
        if (vipRenewDO == null) return null;
        VipRenewModel vipRenewModel = new VipRenewModel();
        BeanUtils.copyProperties(vipRenewDO, vipRenewModel);
        return vipRenewModel;
    }
}