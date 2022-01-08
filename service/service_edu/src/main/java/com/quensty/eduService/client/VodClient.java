package com.quensty.eduService.client;

import com.quensty.commonutils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/20 15:00
 */
@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    /**
     * 定义调用方法路径
     * 根据视频id删除阿里云视频
     * 注解@PathVariable需指定参数名称
     * @param videoId   视频id
     * @return
     */
    @DeleteMapping("/edu-vod/video/removeVideo/{videoId}")
    CommonResult removeVideoById(@PathVariable("videoId") String videoId);

    @DeleteMapping("/edu-vod/video/deleteBatch")
    public CommonResult deleteBatch(@RequestParam("videoList") List<String> videoList);
}
