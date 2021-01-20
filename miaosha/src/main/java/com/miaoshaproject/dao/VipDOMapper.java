package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.VipDO;

import java.util.List;

public interface VipDOMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByUserId(Integer userId);

    int insert(VipDO record);

    int insertSelective(VipDO record);

    VipDO selectByPrimaryKey(Integer id);

    VipDO selectByUserId(Integer userId);

    List<VipDO> listVip();

    List<VipDO> listVipByLevel();

    int updateByPrimaryKeySelective(VipDO record);

    int updateByUserIdSelective(VipDO record);

    int updateByPrimaryKey(VipDO record);
}