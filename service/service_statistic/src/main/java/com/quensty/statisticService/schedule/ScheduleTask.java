package com.quensty.statisticService.schedule;

import com.quensty.statisticService.service.StatisticsDailyService;
import com.quensty.statisticService.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/5 11:45
 */
@Component
@Slf4j
public class ScheduleTask {
    @Autowired
    StatisticsDailyService statisticsDailyService;

    /**
     * 每隔五秒执行一次
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleTaskTest(){
        log.info("**********定时任务：TEST");
    }

    /**
     * 每天凌晨1点统计前一天注册人数
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void countRegisterSchedule(){
        statisticsDailyService.countRegisters(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
        log.info("**********定时任务：统计注册人数");
    }
}
