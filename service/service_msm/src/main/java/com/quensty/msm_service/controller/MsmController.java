package com.quensty.msm_service.controller;

import com.quensty.commonutils.CommonResult;
import com.quensty.msm_service.service.MsmService;
import com.quensty.msm_service.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/28 14:36
 */
@RestController
@RequestMapping("/edu-msm/msm")
//@CrossOrigin
@Api(tags = "阿里云短信服务")
public class MsmController {

    @Autowired
    MsmService msmService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @ApiOperation("发送验证码")
    @GetMapping("/send/{phone}")
    public CommonResult sendVerificationCode(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get("vfCode::" + phone);
        if(!StringUtils.isEmpty(code)){
            return CommonResult.success();
        }

        //生成随机数传到阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean flag = msmService.send(param, phone);
        if (flag) {
            //发送成功将验证码存入redis并设置过期时间
            redisTemplate.opsForValue().set("vfCode::" + phone,code,5, TimeUnit.MINUTES);
            return CommonResult.success();
        }else {
            return  CommonResult.error().message("短信发送失败!");
        }
    }
}
