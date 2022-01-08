package com.quensty.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.quensty.commonutils.CommonResult;
import com.quensty.serviceBase.handler.GuLiException;
import com.quensty.vod.service.VodService;
import com.quensty.vod.utils.ConstantVodUtils;
import com.quensty.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/18 12:32
 */
@RestController
@RequestMapping("/edu-vod/video")
//@CrossOrigin
@Api(tags = "阿里云VodController")
public class VodController {
    @Autowired
    VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadAliyunVideo")
    public CommonResult uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return CommonResult.success().data("videoId",videoId);
    }

    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("/removeVideo/{videoId}")
    public CommonResult removeVideoById(@PathVariable String videoId){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        return CommonResult.success();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除视频失败");
        }
    }

    @DeleteMapping("/deleteBatch")
    public CommonResult deleteBatch(@RequestParam("videoList")List videoList){
        vodService.removeVideoBatch(videoList);
        return CommonResult.success();
    }

    @GetMapping("/getVideoPlayAuth/{videoId}")
    public CommonResult getVideoPlayAuth(@PathVariable String videoId) throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        //创建获取凭证request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

        //request设置视频id
        request.setVideoId(videoId);
        //设置凭证有效时间
        request.setAuthInfoTimeout(200L);
        //调用方法得到凭证
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
        return CommonResult.success().data("playAuth",playAuth);
    }
}
