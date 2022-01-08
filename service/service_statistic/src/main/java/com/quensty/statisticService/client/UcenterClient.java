package com.quensty.statisticService.client;

import com.quensty.commonutils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/4 18:03
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/ucenter/member/countRegisters/{date}")
    CommonResult countRegisters(@PathVariable("date") String date);
}
