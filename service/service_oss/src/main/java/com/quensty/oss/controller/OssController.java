package com.quensty.oss.controller;

import com.quensty.commonutils.CommonResult;
import com.quensty.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/1 21:50
 */
@Api(tags="阿里云OSS")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    /**
     * 上传头像的方法
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public CommonResult uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回上传到oss的路径
        String url =  ossService.uploadFileAvatar(file);
        return CommonResult.success().data("url",url);
    }
}
