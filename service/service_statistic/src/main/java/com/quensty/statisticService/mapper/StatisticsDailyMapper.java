package com.quensty.statisticService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quensty.statisticService.entity.StatisticsDaily;
import com.quensty.statisticService.entity.vo.statisDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author quensty
 * @since 2022-01-04
 */
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {
    List<statisDataVo> queryDataForStatistic(@Param("type") String type,
                                             @Param("begin") String begin,
                                             @Param("end") String end);
}
