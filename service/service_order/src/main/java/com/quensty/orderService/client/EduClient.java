package com.quensty.orderService.client;

import dto.CourseInfoForOrder;
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
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程id获取课程信息
     * @param id
     * @return
     */
    @PostMapping("/eduService/edu-course/getCourseInfoForOrder/{id}")
    public CourseInfoForOrder getCourseInfoFroOrder(@PathVariable("id") String id);
}
