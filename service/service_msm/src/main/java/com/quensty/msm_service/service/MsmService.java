package com.quensty.msm_service.service;

import java.util.HashMap;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/28 14:37
 */
public interface MsmService {
    /**
     * 发送短信
     * @param param 随机验证码
     * @param phone 手机号
     * @return
     */
    boolean send(HashMap<String, Object> param, String phone);
}
