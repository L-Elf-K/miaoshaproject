package com.miaoshaproject.controller;

import com.miaoshaproject.annotation.LogAnnotation;
import com.miaoshaproject.controller.viewobject.LogVO;
import com.miaoshaproject.dao.LogDOMapper;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.LogService;
import com.miaoshaproject.service.model.LogModel;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/14
 */
@Controller("log")
@RequestMapping("/log")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @LogAnnotation(operModel = "日志接口", operType = "日志查询", operDesc = "日志查询功能")
    @RequestMapping(value = "/querybytime", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType queryLogByTime (@RequestParam(name = "dateTime") Date dateTime) throws BusinessException {
        List<LogModel> logModels = logService.queryLogByTime(dateTime);
        if (logModels == null) throw new BusinessException(EmBusinessError.LOG_QUERY_ERROR);
        List<LogVO> logVOList = logModels.stream().map(logModel -> {
            LogVO logVO = this.convertLogVOFromLogModel(logModel);
            return logVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(logVOList);
    }
    private LogVO convertLogVOFromLogModel(LogModel logModel) {
        if (logModel == null) return null;
        LogVO logVO = new LogVO();
        BeanUtils.copyProperties(logModel, logVO);
        logVO.setOperCreateTime(logModel.getOperCreateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        return logVO;
    }
}