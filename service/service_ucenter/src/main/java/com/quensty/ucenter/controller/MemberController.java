package com.quensty.ucenter.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.commonutils.JwtUtils;
import com.quensty.ucenter.entity.UcenterMember;
import com.quensty.ucenter.entity.vo.LoginVo;
import com.quensty.ucenter.entity.vo.RegisterVo;
import com.quensty.ucenter.service.MemberService;
import dto.UserInfoForOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author quensty
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/ucenter/member")
//@CrossOrigin
@Api(tags = "用户登录模块")
public class MemberController {

    @Autowired
    MemberService memberService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonResult loginUser(@RequestBody LoginVo loginVo){
        //调用service登录
        //返回token值，使用jwt生成
        String token = memberService.login(loginVo);
        return CommonResult.success().data("token",token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return CommonResult.success();
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getUserProfile")
    public CommonResult getUserProfile(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember profile = memberService.getById(memberId);
        return CommonResult.success().data("userProfile",profile);
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/getUserInfoForOrder/{id}")
    public UserInfoForOrder getUserInfo(@PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        UserInfoForOrder userInfoForOrder = new UserInfoForOrder();
        BeanUtils.copyProperties(ucenterMember,userInfoForOrder);
        return userInfoForOrder;
    }

    @GetMapping("/countRegisters/{date}")
    public CommonResult countRegisters(@PathVariable String date){
        Integer count = memberService.countRegisters(date);
        return CommonResult.success().data("countRegisters",count);
    }
}

