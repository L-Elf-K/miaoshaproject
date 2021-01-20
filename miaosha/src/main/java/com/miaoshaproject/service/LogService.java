package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.LogModel;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by kk on 2021/1/14
 */
public interface LogService {

    void saveLog(LogModel logModel) throws BusinessException;

    List<LogModel> queryLogByTime(Date dateTime) throws BusinessException;
}
