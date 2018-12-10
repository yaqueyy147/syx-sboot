package com.syx.sboot.controller;

import com.syx.sboot.AdminBaseController;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.entity.TFamily;
import com.syx.sboot.mapper.family.TFamilyMapper;
import com.syx.sboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/22 0022.
 */
@RestController
public class TestController extends AdminBaseController {

    @Autowired
    private TFamilyMapper tFamilyMapper;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private TestService01 testService01;
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/sboot/index")
    public ModelAndView index(Model model){
        model.addAttribute("test","这就是一个test-->Hello World!");

        List<Map<String,Object>> ll4 = tFamilyMapper.getFamily();
        model.addAttribute("ll4",ll4);

//        String ss = restTemplate.getForObject("http://syx-zuul/api/test?str=test01",String.class);
//        model.addAttribute("testcloud",ss);

        String ss = testService.test("test01");
        model.addAttribute("testcloud",ss);
//        model.addAttribute("testcloud",ss);
        return new ModelAndView("hello");
    }

    @RequestMapping(value = "/sboot/query")
    @ResponseBody
    public Object query(HttpServletRequest request, HttpServletResponse response){

//        TFamily tFamily = tFamilyMapper.getOne(new TFamily("39"));
        List<TFamily> ll = tFamilyMapper.getListByCondition(new TFamily(), " limit 0,5");

        return ll;
    }

    @RequestMapping(value = "/sboot/query01")
    @ResponseBody
    public Object query01(HttpServletRequest request, HttpServletResponse response, @RequestBody TFamily tFamily){

        String familyid = WebUtil.getPoPropertyByRequest(request,"id");

        TFamily tFamily1 = tFamilyMapper.getOneById(new TFamily(tFamily.getId()));
//        List<TFamily> ll = tFamilyMapper.getListByCondition(new TFamily(), "");

        return tFamily1;
    }


}
