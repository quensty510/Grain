package com.quensty.orderService.service;

import com.quensty.orderService.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author quensty
 * @since 2022-01-02
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成订单微信支付二维码
     * @param orderNum
     * @return
     */
    Map createCode(String orderNum);

    /**
     * 根据订单号查询订单支付状态
     * @param orderNum
     * @return
     */
    Map<String, String> getPayStatusByOrderNum(String orderNum);

    /**
     * 更新订单支付状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
