package com.yzx.oss.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yzx
 * @create 2022-11-22 23:44
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
public class ConstantOssPropertiesUtils {
    public static String ENDPOINT;
    public static String SECRET;
    public static String BUCKETNAME;
    public static String ACCESS_KEY_ID;
    public static String BUCKET;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        ConstantOssPropertiesUtils.ENDPOINT = ENDPOINT;
    }

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        ConstantOssPropertiesUtils.SECRET = SECRET;
    }

    public String getBUCKETNAME() {
        return BUCKETNAME;
    }

    public void setBUCKETNAME(String BUCKETNAME) {
        ConstantOssPropertiesUtils.BUCKETNAME = BUCKETNAME;
    }

    public String getAccessKeyId() {
        return ACCESS_KEY_ID;
    }

    public void setAccessKeyId(String accessKeyId) {
        ACCESS_KEY_ID = accessKeyId;
    }

    public String getBUCKET() {
        return BUCKET;
    }

    public void setBUCKET(String BUCKET) {
        ConstantOssPropertiesUtils.BUCKET = BUCKET;
    }
}
