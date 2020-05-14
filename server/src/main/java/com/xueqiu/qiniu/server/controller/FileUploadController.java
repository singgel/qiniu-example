package com.xueqiu.qiniu.server.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author xueqiu
 * @description: TODO
 * @date 2020/3/13
 */
@RestController
public class FileUploadController {

    @RequestMapping(value = "uploadFile", method = RequestMethod.GET)
    public String uploadFile() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "-H0vR9I2r86FJHCRR3L7HtmDGZh2Qi_mUOZANZEn";
        String secretKey = "_SL8HUtUCAXszA5WhV2NL2v61VPdWkNZgV3aTPsQ";
        String bucket = "heks-bucket-1";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/xueqiu/IdeaProjects/qiniu-example/file/3V2A0710.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            ObjectMapper mapper = new ObjectMapper();
            DefaultPutRet putRet = mapper.readValue(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return mapper.writeValueAsString(putRet);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "putRet";
    }
}
