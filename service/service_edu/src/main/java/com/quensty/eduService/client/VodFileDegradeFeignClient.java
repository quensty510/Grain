package com.quensty.eduService.client;

import com.quensty.commonutils.CommonResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断器，远程调用出错时执行重写的方法
 * @author quensty
 * @version 1.0
 * @date 2021/11/23 10:44
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public CommonResult removeVideoById(String videoId) {
        return CommonResult.failed().message("删除视频超时！");
    }

    @Override
    public CommonResult deleteBatch(List<String> videoList) {
        return CommonResult.failed().message("批量删除视频超时！");
    }
}
