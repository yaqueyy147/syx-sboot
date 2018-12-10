package com.syx.sboot.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by sux on 2018/12/7 0007.
 */
@FeignClient(name = "syx-zuul")
@Repository("testService")
public interface TestService {
    @RequestMapping(value="/api/test")
    public String test(@RequestParam(value = "str") String str);
}
