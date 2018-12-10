package com.syx.sboot.provider.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by suyx on 2018/12/7 0007.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam String str){
        return "这是测试的provider-02-->" + str + "--002";
    }

}
