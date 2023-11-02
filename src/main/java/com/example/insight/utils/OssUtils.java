package com.example.insight.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.insight.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/31 上午9:19
 * @Version: 1.0
 */

//阿里存储工具类
@Component
public class OssUtils {

    @Autowired
    private OssConfig ossConfig;

    //图片上传
    public String upload(String fileName, InputStream inputStream) {
        // 创建OSSClient
        OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret());
        System.out.println(fileName);
        // 上传文件流
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如 images/2020/11/11/asdf.jpg。
        // 上传头像
        String objectName = "avatar/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        // meta设置请求头,解决访问图片地址直接下载
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        ossClient.putObject(ossConfig.getBucketName(), objectName, inputStream, meta);

        // 关闭OSSClient。
        ossClient.shutdown();

        return ossConfig.getUrl() + "/" + objectName;
    }

    //获取图片类型
    public String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        // if (FilenameExtension.equalsIgnoreCase(".gif")) {
        //     return "image/gif";
        // }
        // if (FilenameExtension.equalsIgnoreCase(".pdf")) {
        //     return "application/pdf";
        // }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }

        return "image/jpg";
    }

    //文件上传
    public String uploadFile(String fileName, InputStream inputStream) {
        // 创建OSSClient
        OSS ossClient = new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret());
        System.out.println(fileName);
        // 上传文件流
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如 images/2020/11/11/asdf.jpg。
        String objectName = "res/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println(objectName);


        // meta设置请求头,解决访问地址直接下载
        ObjectMetadata meta = new ObjectMetadata();
        // meta.setContentType(getContentTypeFile(fileName.substring(fileName.lastIndexOf("."))));
        ossClient.putObject(ossConfig.getBucketName(), objectName, inputStream, meta);

        // 关闭OSSClient。
        ossClient.shutdown();

        return ossConfig.getUrl() + "/" + objectName;
    }


    //获取文件类型
    public String getContentTypeFile(String FilenameExtension) {

        return "application/zip";
    }
}

