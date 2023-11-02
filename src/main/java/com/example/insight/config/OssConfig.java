package com.example.insight.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/31 上午9:17
 * @Version: 1.0
 */

//oss存储配置
@Configuration
@ConfigurationProperties("al.oss")
@Data
public class OssConfig {
    //区域
    private String endpoint;
    //访问id
    private String accessKeyId;
    //访问秘钥
    private String accessKeySecret;
    //桶名称
    private String bucketName;
    //访问URL
    private String url;
}