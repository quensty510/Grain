package com.quensty.orderService.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.orderService.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付日志表 前端控制器
 * @author quensty
 * @since 2022-01-02
 */
@RestController
@RequestMapping("/orderService/pay-log")
//@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * @param orderNum  订单号
     * @return 包含二维码地址和其他信息
     */
    @GetMapping("/createCode/{orderNum}")
    public CommonResult createCode(@PathVariable String orderNum){
        Map map = payLogService.createCode(orderNum);
        System.out.println("*************生成二维码返回结果-->" + map);
        return CommonResult.success().data(map);
    }

    /**
     * 查询订单支付状态
     * @param orderNum 订单号
     * @return
     */
    @GetMapping("/getOrderPayStatus/{orderNum}")
    public CommonResult getOrderPayStatus(@PathVariable String orderNum){
         Map<String,String> map = payLogService.getPayStatusByOrderNum(orderNum);
        System.out.println("******************查询支付状态-->" + map);
         if(map == null){
             return CommonResult.error().message("支付出错");
         }
        //若map不为空，通过map获取订单状态
        if("SUCCESS".equals(map.get("trade_state"))){
            //支付成功
            //添加记录到支付记录表，并更新订单支付状态
            payLogService.updateOrderStatus(map);
            return CommonResult.success().message("支付成功");
        }
        return CommonResult.success().code(25000).message("支付中");
    }
}

