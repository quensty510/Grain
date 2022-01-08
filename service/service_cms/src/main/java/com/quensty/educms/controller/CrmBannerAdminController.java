package com.quensty.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.commonutils.CommonResult;
import com.quensty.educms.entity.CrmBanner;
import com.quensty.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author quensty
 * @since 2021-11-25
 */
@RestController
@RequestMapping("/educms/admin/banner")
//@CrossOrigin
@Api(tags = "后台系统轮播图接口")
public class CrmBannerAdminController {
    @Autowired
    CrmBannerService bannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("/pageBanner/{pageNum}/{pageSize}")
    public CommonResult pageBanner(@PathVariable("pageNum")long pageNum,@PathVariable("pageSize") long pageSize){
        Page<CrmBanner> bannerPage = new Page<>(pageNum,pageSize);
        bannerService.page(bannerPage,null);
        return CommonResult.success().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("/addBanner")
    public CommonResult addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public CommonResult get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return CommonResult.success().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public CommonResult updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public CommonResult remove(@PathVariable String id) {
        bannerService.removeById(id);
        return CommonResult.success();
    }
}

