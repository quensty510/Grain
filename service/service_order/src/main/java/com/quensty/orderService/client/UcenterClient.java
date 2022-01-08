package com.quensty.orderService.client;

import dto.UserInfoForOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/3 11:25
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/ucenter/member/getUserInfoForOrder/{id}")
    public UserInfoForOrder getUserInfo(@PathVariable("id") String id);
}
