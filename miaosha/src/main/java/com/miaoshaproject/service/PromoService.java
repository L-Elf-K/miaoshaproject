package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * Created by kk on 2020/12/26
 */
public interface PromoService {
    //根据itemid获取即将进行或正在进行的秒杀活动信息
    PromoModel getPromoByItemId(Integer itemId);
}
