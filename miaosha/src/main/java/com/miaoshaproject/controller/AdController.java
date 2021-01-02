package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.AdVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.model.AdModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kk on 2021/1/2
 */
@Controller("ad")
@RequestMapping("/ad")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class AdController extends BaseController{

    @Autowired
    AdService adService;

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getAd(@RequestParam(name = "id")Integer id) throws BusinessException {
        AdModel adModel = adService.getAd(id);
        if (adModel == null) throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"广告不存在！");
        AdVO adVO = this.convertAdVOFromAdMoel(adModel);
        return CommonReturnType.create(adVO);
    }
    private AdVO convertAdVOFromAdMoel(AdModel adModel) {
        AdVO adVO = new AdVO();
        BeanUtils.copyProperties(adModel, adVO);
        return adVO;
    }
}
