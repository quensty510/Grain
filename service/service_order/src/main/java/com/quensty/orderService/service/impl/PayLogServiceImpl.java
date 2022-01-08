package com.quensty.orderService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.quensty.orderService.entity.Order;
import com.quensty.orderService.entity.PayLog;
import com.quensty.orderService.mapper.PayLogMapper;
import com.quensty.orderService.service.OrderService;
import com.quensty.orderService.service.PayLogService;
import com.quensty.orderService.utils.HttpClient;
import com.quensty.serviceBase.handler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author quensty
 * @since 2022-01-02
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    OrderService orderService;

    @Override
    public Map createCode(String orderNum) {
        try {
            //1、根据订单号查询订单信息
            Order orderInfo = orderService.getOrderInfo(orderNum);
            //2、使用map设置生成二维码需要的参数
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", orderInfo.getCourseTitle());
            m.put("out_trade_no", orderNum);
            m.put("total_fee", orderInfo.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //3、发送httpClient请求，传递参数xml格式、微信支付提供的固定地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();
            //4、得到请求结果
            //返回内容为xml格式
            String content = client.getContent();
            //将xml格式转换为map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            //封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNum);
            map.put("course_id", orderInfo.getCourseId());
            map.put("total_fee", orderInfo.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            return  map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001,"生成支付二维码失败");
        }
    }

    @Override
    public Map<String, String> getPayStatusByOrderNum(String orderNum) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNum);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNum = map.get("out_trade_no");
        //根据订单id查询订单信息
        Order orderInfo = orderService.getOrderInfo(orderNum);
        //更新订单支付状态
        if(orderInfo.getStatus().intValue() == 1){return;}
        orderInfo.setStatus(1);
        orderService.updateById(orderInfo);

        //向支付记录表添加支付信息
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderInfo.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(orderInfo.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);//插入到支付日志表

    }
}
