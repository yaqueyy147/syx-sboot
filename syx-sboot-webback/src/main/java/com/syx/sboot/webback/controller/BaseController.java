package com.syx.sboot.webback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by Administrator on 2018/11/30 0030.
 */
@Controller
public class BaseController {

    @RequestMapping(value = {"","/","/index"})
    public RedirectView index(){
        return new RedirectView("/admin/login.htm");
    }

}
