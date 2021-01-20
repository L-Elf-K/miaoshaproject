package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.LogDOMapper;
import com.miaoshaproject.dataobject.LogDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.LogService;
import com.miaoshaproject.service.model.LogModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/14
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    LogDOMapper logDOMapper;

    @Override
    public void saveLog(LogModel logModel) {
        LogDO logDO = this.convertLogDOFromLogModel(logModel);
        logDOMapper.insertSelective(logDO);
    }

    private LogDO convertLogDOFromLogModel(LogModel logModel) {
        if (logModel == null) return null;
        LogDO logDO = new LogDO();
        BeanUtils.copyProperties(logModel, logDO);
        logDO.setOperCreateTime(logModel.getOperCreateTime().toDate());
        return logDO;
    }

    @Override
    public List<LogModel> queryLogByTime(Date dateTime) throws BusinessException {
        List<LogDO> logDOList = logDOMapper.selectByTime(dateTime);
        if (logDOList == null) throw new BusinessException(EmBusinessError.LOG_QUERY_ERROR);
        List<LogModel> logModels = logDOList.stream().map(logDO -> {
            LogModel logModel = this.convertLogModelFromLogDO(logDO);
            return logModel;
        }).collect(Collectors.toList());
        return logModels;
    }

    private LogModel convertLogModelFromLogDO(LogDO logDO) {
       LogModel logModel= new LogModel();
       BeanUtils.copyProperties(logDO, logModel);
       logModel.setOperCreateTime(new DateTime(logDO.getOperCreateTime()));
       return logModel;
    }
}
