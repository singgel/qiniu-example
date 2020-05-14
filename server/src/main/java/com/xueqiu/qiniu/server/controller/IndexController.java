package com.xueqiu.qiniu.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqiu
 */
@Controller
public class IndexController {
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }
}
