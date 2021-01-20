package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.LogDO;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.List;

public interface LogDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogDO record);

    int insertSelective(LogDO record);

    LogDO selectByPrimaryKey(Integer id);

    List<LogDO> selectByTime(Date dateTime);

    int updateByPrimaryKeySelective(LogDO record);

    int updateByPrimaryKeyWithBLOBs(LogDO record);

    int updateByPrimaryKey(LogDO record);
}