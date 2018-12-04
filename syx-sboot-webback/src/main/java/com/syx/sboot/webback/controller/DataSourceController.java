package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Sysdatasource;
import com.syx.sboot.webback.service.SysdatasourceService;
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
public class DataSourceController extends AdminBaseController {
    private static final String INDEX_PATH = "";

    @Autowired
    private SysdatasourceService datasourceService;

    @RequestMapping(value = "/datasourcemanage.htm")
    public ModelAndView datasourcemanage(){
        return new ModelAndView(INDEX_PATH + "datasourcemanage");
    }

    @RequestMapping("querydatasourceList.htm")
    @ResponseBody
    public void querydatasourceList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        Sysdatasource paramobj = new Sysdatasource();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        if(!StringUtils.isEmpty(paramobj.getDsname())){
            sb.append(" and dsname like '%"+paramobj.getDsname()+"%'");
        }
        if(!StringUtils.isEmpty(paramobj.getDscode())){
            sb.append(" and dscode like '%"+paramobj.getDscode()+"%'");
        }
        sb.append("order by createdate desc");
        //根据查询条件查询机构信息
        Page<Sysdatasource> page = datasourceService.findPageByCondition(new Page<Sysdatasource>(request, response), paramobj, sb.toString());
        json.put("page", page);
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("datasourceSaveData.htm")
    @ResponseBody
    public void datasourceSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        Sysdatasource paramobj = new Sysdatasource();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        Sysdatasource saveobj = new Sysdatasource();
        try {
            if (StringUtils.isEmpty(paramobj.getId())) {
                saveobj.setIsNewRecord(true);
                saveobj.setCreateid(UserUtils.getUserid(request));
                saveobj.setCreatename(UserUtils.getUsername(request));
            } else {
                saveobj = datasourceService.get(new Sysdatasource(paramobj.getId()));
            }

            saveobj.setDscode(paramobj.getDscode());
            saveobj.setDsname(paramobj.getDsname());
            saveobj.setDsdriver(paramobj.getDsdriver());
            saveobj.setDsurl(paramobj.getDsurl());
            saveobj.setDsusername(paramobj.getDsusername());
            saveobj.setDspassword(paramobj.getDspassword());
            saveobj.setDsmaxactive(paramobj.getDsmaxactive());
            saveobj.setDsmaxidle(paramobj.getDsmaxidle());
            saveobj.setDscheckouttime(paramobj.getDscheckouttime());
            saveobj.setDswaittime(paramobj.getDswaittime());
            saveobj.setDspingenabled(paramobj.getDspingenabled());
            saveobj.setDspingquery(paramobj.getDspingquery());
            saveobj.setDspingtime(paramobj.getDspingtime());
            saveobj.setDsasorg(0);
            saveobj.setDsremark(paramobj.getDsremark());
            saveobj.setDataversion(0);
            saveobj.setDatavalid(paramobj.getDatavalid());
            saveobj.setRemark(paramobj.getRemark());
            
            if(datasourceService.save(saveobj)==true){
            	json.put("code", 1);
                json.put("message", "保存成功");
            }            
        } catch (Exception e) {
            e.printStackTrace();
           
        }
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("datasourceDeleteData.htm")
    @ResponseBody
    public void datasourceDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        try {
            String ids = WebUtil.getPoPropertyByRequest(request, "ids");
            if(!StringUtils.isEmpty(ids)){
                List<String> listids = StringUtils.stringSplitToList(ids,",");
                if(listids!= null && listids.size() > 0){
                    for(String id: listids){
                        Sysdatasource delobj = datasourceService.get(new Sysdatasource(id));
                        if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
                            delobj.setDeleteflag(Global.YES);
                            datasourceService.save(delobj);
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
