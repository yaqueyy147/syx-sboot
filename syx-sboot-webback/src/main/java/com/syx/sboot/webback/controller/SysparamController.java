package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Sysparam;
import com.syx.sboot.webback.service.SysparamService;
import com.syx.sboot.webback.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by suyx on 2017/7/26 0026.
 */
@Controller
@RequestMapping(value = "/admin/backstage/sys")
public class SysparamController extends AdminBaseController {
    private static final String INDEX_PATH = "";

    @Autowired
    private SysparamService sysparamService;

    @RequestMapping(value = "/parammanage.htm")
    public ModelAndView datasourcemanage(){
        return new ModelAndView(INDEX_PATH + "sysparammanage");
    }

    @RequestMapping("queryparamList.htm")
    @ResponseBody
    public void queryparamList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        Sysparam paramobj = new Sysparam();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        if(!StringUtils.isEmpty(paramobj.getParamkey())){
            sb.append(" and paramkey like '%"+paramobj.getParamkey()+"%'");
        }
        if(!StringUtils.isEmpty(paramobj.getParamvalue())){
            sb.append(" and paramvalue like '%"+paramobj.getParamvalue()+"%'");
        }
        sb.append(" order by paramkey desc, createdate desc");
        //根据查询条件查询机构信息
        Page<Sysparam> page = sysparamService.findPageByCondition(new Page<Sysparam>(request, response), paramobj, sb.toString());
        json.put("page", page);
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("paramSaveData.htm")
    @ResponseBody
    public void paramSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        Sysparam paramobj = new Sysparam();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        Sysparam saveobj = new Sysparam();
        try {
            if (StringUtils.isEmpty(paramobj.getId())) {
                saveobj.setIsNewRecord(true);
                saveobj.setCreateid(UserUtils.getUserid(request));
                saveobj.setCreatename(UserUtils.getUsername(request));
            } else {
                saveobj = sysparamService.get(new Sysparam(paramobj.getId()));
            }

            saveobj.setParamkey(paramobj.getParamkey());
            saveobj.setParamvalue(paramobj.getParamvalue());
            saveobj.setDescription(paramobj.getDescription());
            sysparamService.save(saveobj);

            json.put("code", 1);
            json.put("message", "保存成功");
        } catch (Exception e) {
            json.put("code", 0);
            json.put("message", "保存失败");
        }
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("paramDeleteData.htm")
    @ResponseBody
    public void paramDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        try {
            String ids = WebUtil.getPoPropertyByRequest(request, "ids");
            if(!StringUtils.isEmpty(ids)){
                List<String> listids = StringUtils.stringSplitToList(ids,",");
                if(listids!= null && listids.size() > 0){
                    for(String id: listids){
                        Sysparam delobj = sysparamService.get(new Sysparam(id));
                        if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
                            delobj.setDeleteflag(Global.YES);
                            sysparamService.save(delobj);
                        }
                    }
                    json.put("code", 1);
                    json.put("message", "保存成功");
                }
            }

        } catch (Exception e) {
            json.put("code", 0);
            json.put("message", "保存失败");
        }
        renderString(response, json, jsonCallBack);
    }

}
