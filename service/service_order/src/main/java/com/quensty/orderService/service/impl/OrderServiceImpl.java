package com.quensty.orderService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.orderService.client.EduClient;
import com.quensty.orderService.client.UcenterClient;
import com.quensty.orderService.entity.Order;
import com.quensty.orderService.mapper.OrderMapper;
import com.quensty.orderService.service.OrderService;
import com.quensty.orderService.utils.OrderNoUtil;
import dto.CourseInfoForOrder;
import dto.UserInfoForOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author quensty
 * @since 2022-01-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;
    @Autowired
    UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        //通过远程调用获取用户信息
        UserInfoForOrder userDto = ucenterClient.getUserInfo(memberIdByJwtToken);
        //通过远程调用获取课程信息
        CourseInfoForOrder courseDto = eduClient.getCourseInfoFroOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userDto.getMobile());
        order.setNickname(userDto.getNickname());

        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public Order getOrderInfo(String orderNum) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNum);
        return baseMapper.selectOne(orderQueryWrapper);
    }
}
