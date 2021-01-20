package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.AdNowVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.AdNowService;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.model.AdModel;
import com.miaoshaproject.service.model.AdNowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kk on 2021/1/20
 */
@Controller("adnow")
@RequestMapping("/adnow")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AdNowController extends BaseController{

    @Autowired
    AdNowService adNowService;

    @Autowired
    AdService adService;

    @RequestMapping("/list")
    @ResponseBody
    public CommonReturnType getAdNowList() throws BusinessException {
        List<AdNowModel> adNowModelList = adNowService.listAdNow();
        List<AdNowVO> adNowVOList = new LinkedList<>();
        for (AdNowModel adNowModel : adNowModelList
             ) {
            AdNowVO adNowVO = new AdNowVO();
            adNowVO.setUserid(adNowModel.getUserId());
            AdModel adModel = adService.getAd(adNowModel.getAdId());
            Map<Integer, List<AdModel>> adMap = new HashMap<>();
            adMap.put(adNowModel.getAdId(), adService.getRelatedAd(adModel.getCategory()));
            adModel.setRelatedAd(adMap);
            adNowVO.setAdModel(adModel);
            adNowVOList.add(adNowVO);
        }
        return CommonReturnType.create(adNowVOList);
    }
}
