package com.quensty.orderService.service;

import com.quensty.orderService.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author quensty
 * @since 2022-01-02
 */
public interface OrderService extends IService<Order> {

    /**
     * 生成订单
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    String createOrders(String courseId, String memberIdByJwtToken);

    /**
     * 根据订单号获取订单信息
     * @param orderNum
     * @return
     */
    Order getOrderInfo(String orderNum);
}
