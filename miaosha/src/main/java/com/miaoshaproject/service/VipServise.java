package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.VipModel;

import java.util.List;

/**
 * Created by kk on 2021/1/1
 */
public interface VipServise {

    //取消VIP权限
    int deleteVip(Integer userId);

    //用户成为VIP
    VipModel createVip(VipModel vipModel) throws BusinessException;

    //vip用户列表查询
    List<VipModel> listVip();
}
