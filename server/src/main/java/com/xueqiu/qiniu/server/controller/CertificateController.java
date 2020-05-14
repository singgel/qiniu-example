package com.xueqiu.qiniu.server.controller;

import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xueqiu
 * @description: TODO
 * @date 2020/3/12
 */
@RestController
public class CertificateController {
    private final static String accessKey = "-H0vR9I2r86FJHCRR3L7HtmDGZh2Qi_mUOZANZEn";
    private final static String secretKey = "_SL8HUtUCAXszA5WhV2NL2v61VPdWkNZgV3aTPsQ";
    private final static String bucket = "heks-bucket-0";
    private final static String domain = "q70pk89uf.bkt.clouddn.com";


    @RequestMapping(value = "upToken", method = RequestMethod.GET)
    public String upToken(){

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return upToken;
    }

    @RequestMapping("/QiniuUpToken")
    @ResponseBody
    public Map<String, Object> QiniuUpToken(@RequestParam String suffix) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //验证七牛云身份是否通过
            Auth auth = Auth.create(accessKey, secretKey);
            //生成凭证
            String upToken = auth.uploadToken(bucket);
            result.put("token", upToken);
            //存入外链默认域名，用于拼接完整的资源外链路径
            result.put("domain", domain);

            // 是否可以上传的图片格式
            /*boolean flag = false;
            String[] imgTypes = new String[]{"jpg","jpeg","bmp","gif","png"};
            for(String fileSuffix : imgTypes) {
                if(suffix.substring(suffix.lastIndexOf(".") + 1).equalsIgnoreCase(fileSuffix)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                throw new Exception("图片：" + suffix + " 上传格式不对！");
            }*/

            //生成实际路径名
            String randomFileName = UUID.randomUUID().toString() + suffix;
            result.put("imgUrl", randomFileName);
            result.put("success", 1);
        } catch (Exception e) {
            result.put("message", "获取凭证失败，"+e.getMessage());
            result.put("success", 0);
        } finally {
            return result;
        }
    }
}
