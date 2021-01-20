package com.miaoshaproject.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Created by kk on 2021/1/20
 */
@Component
public class SoutTask implements DisposableBean {
    @Autowired
    private AbstractApplicationContext applicationContext;

    @Override
    public void destroy() throws Exception {
        ThreadPoolTaskScheduler scheduler = (ThreadPoolTaskScheduler) applicationContext.getBean("scheduler");
        scheduler.shutdown();
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void work(){
        System.out.println("我执行了");
    }
}
