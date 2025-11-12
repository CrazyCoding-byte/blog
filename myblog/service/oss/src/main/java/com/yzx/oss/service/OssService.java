package com.yzx.oss.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.yzx.oss.Utils.ConstantOssPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author yzx
 * @create 2022-11-22 23:43
 */
@Service
public class OssService {
    public String upload(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantOssPropertiesUtils.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantOssPropertiesUtils.ACCESS_KEY_ID;
        System.out.println(accessKeyId);
        String accessKeySecret = ConstantOssPropertiesUtils.SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantOssPropertiesUtils.BUCKETNAME;
        String bucket = ConstantOssPropertiesUtils.BUCKET;
        //返回我们想要的图片路径
        String originalFilename = null;

        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = null;

            inputStream = file.getInputStream();
            originalFilename = file.getOriginalFilename();
            //生成随机唯一值没使用uuid，添加到文件名称里面
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            originalFilename = uuid + originalFilename;

            //按照当前日期，创建文件夹，上传到创建文件夹里
            // /2021/02/02/ 01.jpg
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            originalFilename = timeUrl + "/" + originalFilename;

            //调用方法实现上传
            // 创建PutObject请求。
            //以下载的方式
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");
            ossClient.putObject(bucketName, originalFilename, inputStream, objectMetadata);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
                //https://yygh-atguigu.oss-cn-beijing.aliyuncs.com/01.jpg
                String url = "https://" + bucket + "/" + originalFilename;
                return url;
            }
        }
        return null;
    }
}
