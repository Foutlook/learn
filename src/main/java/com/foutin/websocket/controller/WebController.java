package com.foutin.websocket.controller;

import com.foutin.websocket.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author xingkai.fan
 * @date 2019/2/19 11:41
 */
@Controller
public class WebController {

    @Autowired
    private WebService webService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/decoding")
    @ResponseBody
    public String decode(@RequestParam(name = "mes") String message){
        String decode = webService.decode(message);
        System.out.println(decode);
        return decode;
    }

    @PostMapping(value = "/encoding")
    @ResponseBody
    public String encode(@RequestParam(name = "mes") String message){
        String encode = webService.encode(message);
        System.out.println(encode);
        return encode;
    }
}