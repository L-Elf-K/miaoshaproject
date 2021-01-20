package com.miaoshaproject.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miaoshaproject.annotation.LogAnnotation;
import com.miaoshaproject.controller.Emvippower.Emvippower;
import com.miaoshaproject.controller.viewobject.VipVO;
import com.miaoshaproject.dataobject.VipRenewDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.VipServise;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.service.model.VipModel;
import com.miaoshaproject.service.model.VipRenewModel;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.catalina.User;
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
    @LogAnnotation(operModel = "会员接口", operType = "会员创建", operDesc = "新建vip落表")
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createVip (@RequestParam(name = "level")Integer level,
                                       @RequestParam(name = "startDate")DateTime startDate,
                                       @RequestParam(name = "duration")Integer month,
                                       @RequestParam(name = "consumption")Double consumption) throws BusinessException {
        VipModel vipModel = new VipModel();
        vipModel.setLevel(level);
        vipModel.setStartDate(startDate);
        vipModel.setEndDate(startDate.plusMonths(month));
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        vipModel.setUserId(userModel.getId());
        vipModel = vipServise.createVip(vipModel);

        userModel.setVipId(vipModel.getId());

        VipRenewModel vipRenewModel = new VipRenewModel();
        vipRenewModel.setDuration(month);
        vipRenewModel.setConsumption(consumption);
        vipRenewModel.setUserId(userModel.getId());
        vipServise.addVipRenew(vipRenewModel);
        return CommonReturnType.create(null);
    }

    @LogAnnotation(operModel = "会员接口", operType = "会员删除", operDesc = "vip删除功能")
    @RequestMapping(value = "/delete",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType deletVipByid(@RequestParam(name = "vipId") Integer id) {
        vipServise.deleteVip(id);
        return CommonReturnType.create(null);
    }

    //vip用户列表页面浏览
    @LogAnnotation(operModel = "会员接口", operType = "会员列表浏览", operDesc = "vip列表浏览功能")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listVip(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "orderByLevel", defaultValue = "false") Boolean orderByLevel) {
        List<VipModel> vipModelList;
        PageHelper.startPage(pageNum, pageSize);
        if (orderByLevel == true) {
            vipModelList = vipServise.listVipByLevel();
        }
        else vipModelList = vipServise.listVip();


        //使用stream api把list内的vipModel转化为vipVO
        List<VipVO> vipVOList = vipModelList.stream().map(vipModel -> {
            VipVO vipVO = convertVOFromModel(vipModel);
            return vipVO;
        }).collect(Collectors.toList());
//        PageInfo pageInfo = new PageInfo(vipModelList);
//        System.out.println("总页数：" + pageInfo.getPages());
//        System.out.println("总记录数：" + pageInfo.getTotal());
//        System.out.println("当前页数：" + pageInfo.getPageNum());
//        System.out.println("当前页面记录数量" + pageInfo.getSize());
        return CommonReturnType.create(vipVOList);
    }
    private VipVO convertVOFromModel(VipModel vipModel) {
        if (vipModel == null) return null;
        VipVO vipVO = new VipVO();
        BeanUtils.copyProperties(vipModel, vipVO);
        switch (vipModel.getLevel()) {
            case 1:
                vipVO.setLevel(Emvippower.LEVEL_1);break;
            case 2:
                vipVO.setLevel(Emvippower.LEVEL_2);break;
            case 3:
                vipVO.setLevel(Emvippower.LEVEL_3);break;
            case 4:
                vipVO.setLevel(Emvippower.LEVEL_4);break;
        }
        vipVO.setPower(vipVO.getLevel().getPower());
        vipVO.setStartDate(vipModel.getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        vipVO.setEndDate(vipModel.getEndDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));

        vipVO.setAge(vipModel.getUserModel().getAge());
        vipVO.setGender(vipModel.getUserModel().getGender());
        vipVO.setName(vipModel.getUserModel().getName());
        vipVO.setTelphone(vipModel.getUserModel().getTelphone());
        return vipVO;
    }

    //vip用户续费接口
    @LogAnnotation(operModel = "会员接口", operType = "会员续费", operDesc = "vip用户续费功能")
    @RequestMapping(value = "/renew", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType renew(@RequestParam(name = "duration")Integer month,
                                  @RequestParam(name = "consumption")Double consumption) throws BusinessException {
        VipRenewModel vipRenewModel = new VipRenewModel();
        vipRenewModel.setDuration(month);
        vipRenewModel.setConsumption(consumption);
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("USER_LOGIN");
        vipRenewModel.setUserId(userModel.getId());
        vipServise.addVipRenew(vipRenewModel);

        VipModel vipModel = vipServise.getVip(userModel.getVipId());
        vipModel.setEndDate(vipModel.getEndDate().plusMonths(month));
        vipServise.updateVipDuration(vipModel);
        return CommonReturnType.create(null);
    }

    //vip用户续费记录查询
    @LogAnnotation(operModel = "会员接口", operType = "会员续费记录浏览", operDesc = "vip续费记录浏览")
    @RequestMapping(value = "/renewlist", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listrenew(@RequestParam(name = "pageNum") Integer pageNum,
                                      @RequestParam(name = "pageSize") Integer pageSize) {
        List<VipRenewModel> vipRenewModelList;
        PageHelper.startPage(pageNum, pageSize);
        vipRenewModelList = vipServise.listVipRenew();
        return CommonReturnType.create(vipRenewModelList);
    }
}
