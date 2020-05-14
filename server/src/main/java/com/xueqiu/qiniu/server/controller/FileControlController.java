package com.xueqiu.qiniu.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqiu
 * @description: TODO
 * @date 2020/3/15
 */
@RestController
public class FileControlController {

    @RequestMapping(value = "fileInfo", method = RequestMethod.GET)
    public String fileInfo() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        String accessKey = "-H0vR9I2r86FJHCRR3L7HtmDGZh2Qi_mUOZANZEn";
        String secretKey = "_SL8HUtUCAXszA5WhV2NL2v61VPdWkNZgV3aTPsQ";
        String bucket = "heks-bucket-0";
        String key = "pic1.jpg";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(bucket, key);
            System.out.println(fileInfo.hash);
            System.out.println(fileInfo.fsize);
            System.out.println(fileInfo.mimeType);
            System.out.println(fileInfo.putTime);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(fileInfo);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "fileInfo";
    }

    @RequestMapping(value = "bucketInfo", method = RequestMethod.GET)
    public String bucketInfo() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        String accessKey = "-H0vR9I2r86FJHCRR3L7HtmDGZh2Qi_mUOZANZEn";
        String secretKey = "_SL8HUtUCAXszA5WhV2NL2v61VPdWkNZgV3aTPsQ";
        String bucket = "heks-bucket-0";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";

        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize);
                System.out.println(item.mimeType);
                System.out.println(item.putTime);
                System.out.println(item.endUser);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(fileListIterator);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "bucketInfo";
    }
}
