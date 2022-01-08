package com.quensty.eduService.controller;

import com.quensty.commonutils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/10/31 20:34
 */
@Api(tags = "登录接口Demo")
@RestController
@RequestMapping("/eduService/user")
//@CrossOrigin
public class EduLoginController {
    /**
     * 登录接口Demo，返回一个token
     * @return
     */
    @ApiOperation("登录接口Demo，返回一个token")
    @PostMapping("/login")
    public CommonResult login(){
        return CommonResult.success().data("token","admin");
    }

    /**
     * 返回用户信息
     * @return
     */
    @ApiOperation("返回用户信息")
    @GetMapping("/info")
    public CommonResult getInfo(){
        return CommonResult.success().data("roles","[admin]").data("name","admin").data("avatar","https://edu-demo-oss.oss-cn-beijing.aliyuncs.com/2021/11/01/92283ffd533e48009b5e533077a1589flubongthethirt.jpg");
    }
}
