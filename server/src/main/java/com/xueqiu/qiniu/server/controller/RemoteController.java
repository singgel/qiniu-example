package com.xueqiu.qiniu.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqiu
 * @description: TODO
 * @date 2020/3/15
 */
@RestController
public class RemoteController {
    @RequestMapping(value = "antiTheftChain", method = RequestMethod.GET)
    public String antiTheftChain() {
        return "antiTheftChain";
    }
}
