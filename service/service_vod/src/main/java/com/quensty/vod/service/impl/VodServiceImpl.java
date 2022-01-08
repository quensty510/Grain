package com.quensty.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.quensty.commonutils.CommonResult;
import com.quensty.serviceBase.handler.GuLiException;
import com.quensty.vod.utils.ConstantVodUtils;
import com.quensty.vod.service.VodService;
import com.quensty.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/18 12:34
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String title = file.getOriginalFilename().substring(0,originalFilename.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(
                ConstantVodUtils.ACCESS_KEY_ID,
                ConstantVodUtils.ACCESS_KEY_SECRET,
                title, originalFilename, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
        // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
        String videoId = "";
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            videoId = response.getVideoId();
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return videoId;
    }

    @Override
    public void removeVideoBatch(List videoList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除视频失败");
        }
    }
}
