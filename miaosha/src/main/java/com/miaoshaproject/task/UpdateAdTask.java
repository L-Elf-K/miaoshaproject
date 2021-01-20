package com.miaoshaproject.task;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.AdNowService;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.ExcService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.AdModel;
import com.miaoshaproject.service.model.AdNowModel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kk on 2021/1/20
 */
@Component
public class UpdateAdTask implements DisposableBean {
    @Autowired
    private AbstractApplicationContext applicationContext;

    @Autowired
    AdNowService adNowService;

    @Autowired
    ExcService excService;

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @Override
    public void destroy() throws Exception {
        ThreadPoolTaskScheduler scheduler = (ThreadPoolTaskScheduler) applicationContext.getBean("scheduler");
        scheduler.shutdown();
    }

    @Scheduled(cron = "0/20 * * * * *")  //秒，分，时，日，月，年
    public void work() throws BusinessException {
        adNowService.deleteTable();
        AdNowModel adNowModel = new AdNowModel();
        List<Integer> userIdList = userService.selectId();
        for (Integer id : userIdList
        ) {
            adNowModel.setUserId(id);
            AdModel adModel = adService.select1AdIdByRand();
            adNowModel.setAdId(adModel.getId());
            adNowService.saveAdNow(adNowModel);
        }
    }
}

