package com.quensty.orderService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quensty.commonutils.CommonResult;
import com.quensty.commonutils.JwtUtils;
import com.quensty.orderService.entity.Order;
import com.quensty.orderService.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单 前端控制器
 * @author quensty
 * @since 2022-01-02
 */
@RestController
@RequestMapping("/orderService/order")
//@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @param courseId
     * @param request 通过请求体中的token获取用户id
     * @return
     */
    @ApiOperation("生成订单")
    @PostMapping("/createOrder/{courseId}")
    public CommonResult createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderNum = orderService.createOrders(courseId,memberIdByJwtToken);
        return CommonResult.success().data("orderNum",orderNum);
    }

    /**
     * 查询订单信息
     * @param orderNum
     * @return
     */
    @GetMapping("/getOrderInfo/{orderNum}")
    public CommonResult getOrderInfo(@PathVariable String orderNum){
        Order order = orderService.getOrderInfo(orderNum);
        return CommonResult.success().data("order",order);
    }

    /**
     * 根据课程id和用户id确认课程购买状态
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/isPurchased/{courseId}/{memberId}")
    public boolean isPurchased(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId)
                    .eq("member_id",memberId)
                    //支付状态，1 代表已支付
                    .eq("status",1);
        int count = orderService.count(queryWrapper);
        return count > 0;
    }

}

