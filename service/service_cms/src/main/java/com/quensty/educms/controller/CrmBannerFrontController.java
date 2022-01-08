package com.quensty.educms.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.educms.entity.CrmBanner;
import com.quensty.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author quensty
 * @since 2021-11-25
 */
@RestController
@RequestMapping("/educms/front/banner")
//@CrossOrigin
@Api(tags = "前台系统轮播图接口")
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取所有banner")
    @GetMapping("/getAllBanner")
    public CommonResult index() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return CommonResult.success().data("bannerList", list);
    }

}

