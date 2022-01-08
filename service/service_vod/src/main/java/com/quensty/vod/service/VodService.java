package com.quensty.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/18 12:33
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeVideoBatch(List videoList);
}
