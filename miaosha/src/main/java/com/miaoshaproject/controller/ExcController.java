package com.miaoshaproject.controller;

import com.miaoshaproject.annotation.LogAnnotation;
import com.miaoshaproject.controller.viewobject.ExcVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ExcService;
import com.miaoshaproject.service.model.ExcModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/18
 */
@Controller("exc")
@RequestMapping("/exc")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ExcController extends BaseController{
    @Autowired
    private ExcService excService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @LogAnnotation(operModel = "异常接口", operType = "异常列表浏览", operDesc = "异常浏览功能")
    @RequestMapping(value = "/querybytime", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType queryLogByTime (@RequestParam(name = "dateTime") Date dateTime) throws BusinessException {
        List<ExcModel> excModelList = excService.queryExcByTime(dateTime);
        if (excModelList == null) throw new BusinessException(EmBusinessError.EXC_QUERY_ERROR);
        List<ExcVO> excVOList = excModelList.stream().map(excModel -> {
            ExcVO excVO = this.convertExcVOFromExcModel(excModel);
            return excVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(excVOList);
    }
    private ExcVO convertExcVOFromExcModel(ExcModel excModel) {
        if (excModel == null) return null;
        ExcVO excVO = new ExcVO();
        BeanUtils.copyProperties(excModel, excVO);
        excVO.setOperCreateTime(excModel.getOperCreateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        return excVO;
    }
}
