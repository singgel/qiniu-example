package com.xueqiu.qiniu.server.controller;

import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author xueqiu
 * @description: TODO
 * @date 2020/3/15
 */
@RestController
public class FileDownloadController {

    @RequestMapping(value = "downLoadFile", method = RequestMethod.GET)
    public String downloadFile() {
        String fileName = "logo.jpeg";
        String domainOfBucket = "q70pirp34.bkt.clouddn.com/";
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        String accessKey = "-H0vR9I2r86FJHCRR3L7HtmDGZh2Qi_mUOZANZEn";
        String secretKey = "_SL8HUtUCAXszA5WhV2NL2v61VPdWkNZgV3aTPsQ";
        Auth auth = Auth.create(accessKey, secretKey);
        //1小时，可以自定义链接过期时间
        long expireInSeconds = 3600;
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);

        System.out.println(finalUrl);
        return finalUrl;
    }
}
