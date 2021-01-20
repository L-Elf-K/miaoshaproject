package com.miaoshaproject.configuration;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.AdNowService;
import com.miaoshaproject.service.AdService;
import com.miaoshaproject.service.ExcService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.AdModel;
import com.miaoshaproject.service.model.AdNowModel;
import com.miaoshaproject.task.UpdateAdTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kk on 2021/1/20
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
//        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
//        UpdateAdTask updateAdTask = new UpdateAdTask();
//        scheduledThreadPool.scheduleAtFixedRate(updateAdTask, 0, 5, TimeUnit.SECONDS);
        return Executors.newScheduledThreadPool(5);
    }
}


