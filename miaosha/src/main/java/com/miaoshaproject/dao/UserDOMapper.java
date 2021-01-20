package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.UserDO;
import org.apache.catalina.User;

import java.util.List;

public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    UserDO selectByTelphone(String telphone);

    List<UserDO> selectId();

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);


}