package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.AdDO;

public interface AdDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdDO record);

    int insertSelective(AdDO record);

    AdDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdDO record);

    int updateByPrimaryKey(AdDO record);
}