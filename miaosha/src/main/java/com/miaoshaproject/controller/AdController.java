package com.miaoshaproject.controller;

import com.miaoshaproject.annotation.LogAnnotation;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.model.AdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kk on 2021/1/2
 */
@Controller("ad")
@RequestMapping("/ad")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class AdController extends BaseController{

    @Autowired
    AdService adService;

    @LogAnnotation(operModel = "广告接口", operType = "获取广告", operDesc = "获取广告功能")
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getAd(@RequestParam(name = "id")Integer id) throws BusinessException {
        AdModel adModel = adService.getAd(id);
        if (adModel == null) throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"广告不存在！");
        Map<Integer, List<AdModel>> adMap = new HashMap<>();
        adMap.put(id, adService.getRelatedAd(adModel.getCategory()));
        adModel.setRelatedAd(adMap);
        return CommonReturnType.create(adModel);
    }
}
