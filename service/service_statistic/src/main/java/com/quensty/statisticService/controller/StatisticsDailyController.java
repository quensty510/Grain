package com.quensty.statisticService.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.statisticService.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author quensty
 * @since 2022-01-04
 */
@RestController
@RequestMapping("/statisticService/statistics-daily")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisService;

    /**
     * 统计某一天注册人数，生成统计数据
     * @param date
     * @return
     */
    @PostMapping("/countRegisters/{date}")
    public CommonResult countRegisters(@PathVariable String date){
        statisService.countRegisters(date);
        return CommonResult.success();
    }

    /**
     * Echarts图标显示
     * @param type 数据类型
     * @param begin
     * @param end
     * @return 日期json数组和数据json数组
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public CommonResult showData(@PathVariable String type,
                                 @PathVariable String begin,
                                 @PathVariable String end){
        Map<String,Object> map = statisService.getShowData(type,begin,end);
        return CommonResult.success().data(map);
    }


}

