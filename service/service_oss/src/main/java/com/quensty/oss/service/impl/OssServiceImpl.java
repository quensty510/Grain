package com.quensty.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.quensty.oss.service.OssService;
import com.quensty.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/1 21:50
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file){
        //工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        try {
            // 获取文件的上传流
            inputStream = file.getInputStream();
            //获取文件名
            String filename = UUID.randomUUID().toString().replaceAll("-","") + file.getOriginalFilename();

            //将文件按照日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;

            //调用oss方法上传文件
            ossClient.putObject(bucketName, filename, inputStream);
            String resultUrl = "https://" + bucketName + "." + endpoint + "/" + filename;
            return resultUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
