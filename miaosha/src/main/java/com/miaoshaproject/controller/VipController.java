package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.VipVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.VipServise;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.service.model.VipModel;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kk on 2021/1/1
 */
@Controller("vip")
@RequestMapping("/vip")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class VipController extends BaseController {

    @Autowired
    private VipServise vipServise;

    @Resource
    private HttpServletRequest httpServletRequest;

    //vip用户创建
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createVip (@RequestParam(name = "level")Integer level,
                                      @RequestParam(name = "startDate")DateTime startDate,
                                      @RequestParam(name = "endDate")DateTime endDate) throws BusinessException {
        VipModel vipModel = new VipModel();
        vipModel.setLevel(level);
        vipModel.setStartDate(startDate);
        vipModel.setEndDate(endDate);
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        vipModel.setUserId(userModel.getId());
        vipServise.createVip(vipModel);
        return CommonReturnType.create(null);
    }

    //vip用户列表页面浏览
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listVip() {
        List<VipModel> vipModelList = vipServise.listVip();

        //使用stream api把list内的vipModel转化为vipVO
        List<VipVO> vipVOList = vipModelList.stream().map(vipModel -> {
            VipVO vipVO = convertVOFromModel(vipModel);
            return vipVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(vipVOList);
    }
    private VipVO convertVOFromModel(VipModel vipModel) {
        if (vipModel == null) return null;
        VipVO vipVO = new VipVO();
        BeanUtils.copyProperties(vipModel, vipVO);
        vipVO.setStartDate(vipModel.getStartDate().toString(DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss")));
        vipVO.setEndDate(vipModel.getEndDate().toString(DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss")));
        return vipVO;
    }
}
