package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.AdDO;

import java.util.List;

public interface AdDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdDO record);

    int insertSelective(AdDO record);

    AdDO selectByPrimaryKey(Integer id);

    List<AdDO> select7AdByCategory(String category);

    AdDO select1AdByRand();

    int updateByPrimaryKeySelective(AdDO record);

    int updateByPrimaryKey(AdDO record);
}