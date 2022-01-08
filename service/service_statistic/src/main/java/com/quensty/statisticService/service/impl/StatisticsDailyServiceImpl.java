package com.quensty.statisticService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.commonutils.CommonResult;
import com.quensty.statisticService.client.UcenterClient;
import com.quensty.statisticService.entity.StatisticsDaily;
import com.quensty.statisticService.entity.vo.statisDataVo;
import com.quensty.statisticService.mapper.StatisticsDailyMapper;
import com.quensty.statisticService.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author quensty
 * @since 2022-01-04
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void countRegisters(String date) {
        //查询之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",date);
        baseMapper.delete(queryWrapper);

        //远程调用得到注册人数
        CommonResult result = ucenterClient.countRegisters(date);
        Integer count = (Integer) result.getData().get("countRegisters");

        //将获取到的数据添加到统计数据表里
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);//统计人数
        statisticsDaily.setDateCalculated(date);//统计日期

        //模拟使用，随机数据
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {

        List<statisDataVo> dataVoList = baseMapper.queryDataForStatistic(type, begin, end);
        //返回日期和日期对应数据，封装数据
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        for (statisDataVo statisDataVo : dataVoList) {
            dateList.add(statisDataVo.getDate());
            countList.add(statisDataVo.getCount());
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("dateList",dateList);
        resultMap.put("countList",countList);
        return resultMap;
    }

}
