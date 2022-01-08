package com.quensty.eduService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/4 15:09
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/orderService/order/isPurchased/{courseId}/{memberId}")
    public boolean isPurchased(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);

}
