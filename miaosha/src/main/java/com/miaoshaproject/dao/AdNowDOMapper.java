package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.AdNowDO;

import java.util.List;

public interface AdNowDOMapper {
    int deleteByPrimaryKey(Integer id);

    void deleteTable();

    int insert(AdNowDO record);

    int insertSelective(AdNowDO record);

    AdNowDO selectByPrimaryKey(Integer id);

    List<AdNowDO> selectAll();

    int updateByPrimaryKeySelective(AdNowDO record);

    int updateByPrimaryKey(AdNowDO record);
}