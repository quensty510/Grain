package com.quensty.eduService.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.client.VodClient;
import com.quensty.eduService.entity.EduVideo;
import com.quensty.eduService.service.EduVideoService;
import com.quensty.serviceBase.handler.GuLiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduService/section")
@Api(tags = "课程小节")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("/addSection")
    public CommonResult addSection(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return CommonResult.success();
    }

    @ApiOperation("删除小节")
    @DeleteMapping("/deleteSection/{id}")
    public CommonResult deleteVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        if (!StringUtils.isEmpty(video.getVideoSourceId())) {
            CommonResult result = vodClient.removeVideoById(video.getVideoSourceId());
            if (result.getCode() == 20001){
                throw new GuLiException(20001,"删除视频超时");
            }
        }
        videoService.removeById(id);
        return CommonResult.success();
    }

    @ApiOperation("修改小节")
    @PutMapping("/updateSection")
    public CommonResult updateSection(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return CommonResult.success();
    }

    @ApiOperation("根据id获取小节信息")
    @GetMapping("/getSectionInfo/{sectionId}")
    public CommonResult getSectionInfoById(@PathVariable String sectionId) {
        EduVideo sectionInfo = videoService.getById(sectionId);
        return CommonResult.success().data("sectionInfo", sectionInfo);
    }

}

