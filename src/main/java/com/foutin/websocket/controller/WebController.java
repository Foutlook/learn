package com.foutin.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xingkai.fan
 * @date 2019/2/19 11:41
 */
@Controller
public class WebController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
