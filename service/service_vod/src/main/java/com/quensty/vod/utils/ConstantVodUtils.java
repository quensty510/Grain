package com.quensty.vod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/18 12:46
 */
@Component
public class ConstantVodUtils implements InitializingBean {
    @Value("${aliyun.vod.file.key-id}")
    private String keyId;

    @Value("${aliyun.vod.file.key-secret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
