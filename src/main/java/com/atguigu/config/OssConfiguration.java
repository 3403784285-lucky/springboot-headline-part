package com.atguigu.config;

import com.atguigu.properties.AliOssProperties;
import com.atguigu.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    /**
     * 如果Spring上下文中没有AliOssUtil bean，则创建一个新的AliOssUtil bean。
     *
     * @param aliOssProperties 包含AliOSS所需配置信息的AliOssProperties对象。
     * @return 配置好的AliOssUtil对象。
     */
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        // 记录日志，表示开始创建阿里云文件上传工具类对象，并打印出配置信息
        log.info("开始创建阿里云文件上传工具类对象：{}", aliOssProperties);
        // 使用aliOssProperties中的属性创建并返回一个新的AliOssUtil对象
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
