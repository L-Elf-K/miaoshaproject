package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.VipRenewDO;

import java.util.List;

public interface VipRenewDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRenewDO record);

    int insertSelective(VipRenewDO record);

    VipRenewDO selectByPrimaryKey(Integer id);

    List<VipRenewDO> listVipRenew();

    int updateByPrimaryKeySelective(VipRenewDO record);

    int updateByPrimaryKey(VipRenewDO record);
}