package com.atguigu.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @return
     */
    public String upload(byte[] content, String objectName) throws IOException {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 获取文件后缀
            String extension = objectName.substring(objectName.lastIndexOf("."));
            // 设置文件Content-Type
            String contentType = getContentType(extension);

            // 设置文件元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(content.length);

            // 上传文件流
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content), metadata);

            // 返回文件的完整URL
            return "https://" + bucketName + "." + endpoint + "/" + objectName;
        } catch (Exception e) {
            throw new IOException("文件上传失败", e);
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }
    }

    private String getContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".bmp":
                return "image/bmp";
            default:
                return "application/octet-stream";
        }
    }
}