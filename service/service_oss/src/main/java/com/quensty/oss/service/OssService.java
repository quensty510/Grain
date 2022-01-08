package com.quensty.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/1 21:50
 */
public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return  oss的url
     */
    String uploadFileAvatar(MultipartFile file);
}
