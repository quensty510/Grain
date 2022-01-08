package com.quensty.statisticService.service;

import com.quensty.statisticService.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author quensty
 * @since 2022-01-04
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegisters(String date);

    Map<String, Object> getShowData(String type, String begin, String end);
}
