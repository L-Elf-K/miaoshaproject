package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.ExcDO;

import java.util.Date;
import java.util.List;

public interface ExcDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExcDO record);

    int insertSelective(ExcDO record);

    ExcDO selectByPrimaryKey(Integer id);

    List<ExcDO> selectByTime(Date dateTime);

    int updateByPrimaryKeySelective(ExcDO record);

    int updateByPrimaryKeyWithBLOBs(ExcDO record);

    int updateByPrimaryKey(ExcDO record);
}