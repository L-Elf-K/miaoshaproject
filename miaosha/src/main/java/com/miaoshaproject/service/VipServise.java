package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.VipModel;
import com.miaoshaproject.service.model.VipRenewModel;

import java.util.List;

/**
 * Created by kk on 2021/1/1
 */
public interface VipServise {

    //取消VIP权限
    int deleteVip(Integer vipId);

    //用户注册VIP
    VipModel createVip(VipModel vipModel) throws BusinessException;

    //获取VIP
    VipModel getVip(Integer id) throws BusinessException;

    //更改VIP信息
    int updateVipDuration(VipModel vipModel);

    //vip用户列表查询
    List<VipModel> listVip();

    List<VipModel> listVipByLevel();

    //vip续费记录查询
    List<VipRenewModel> listVipRenew();

    //vip续费记录
    VipRenewModel addVipRenew(VipRenewModel vipRenewModel) throws BusinessException;
}
