package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Qzstoreset;
import com.syx.sboot.webback.service.QzstoresetService;
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

@Controller
@RequestMapping(value = "/admin/backstage/sys")
public class QzStoresetController extends AdminBaseController {

    private static final String INDEX_PATH = "";

    @Autowired
    private QzstoresetService rulestoreService;

    /**
     * 账号设置页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/qzstoreset.htm")
    public ModelAndView bqapplication(Model model){
        return new ModelAndView(INDEX_PATH + "/qzstoreset");
    }

    /**
     * 分页查询账号列表
     * @param request
     * @param response
     * @param model
     * @param jsonCallBack
     */
    @RequestMapping("queryqzstoresetList.htm")
    @ResponseBody
    public void queryqzstoresetList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        Qzstoreset paramobj = new Qzstoreset();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        if(!StringUtils.isEmpty(paramobj.getServername())){
            sb.append(" and servername like '%"+paramobj.getServername()+"%'");
        }

        //根据查询条件查询机构信息
        Page<Qzstoreset> page = rulestoreService.findPageByCondition(new Page<Qzstoreset>(request, response), paramobj, sb.toString());
        json.put("page", page);
        renderString(response, json, jsonCallBack);
    }

     /**
     * 新增/修改账号
     * @param request
     * @param response
     * @param model
     * @param jsonCallBack
     */
    @RequestMapping("qzstoresetSaveData.htm")
    @ResponseBody
    public void qzstoresetSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        Qzstoreset paramobj = new Qzstoreset();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        Qzstoreset saveobj = new Qzstoreset();
        try {
            if (StringUtils.isEmpty(paramobj.getId())) {
            	saveobj.setIsNewRecord(true);
                saveobj.setCreateid(UserUtils.getUserid(request));
                saveobj.setCreatename(UserUtils.getUsername(request));
                saveobj.setDeleteflag(Global.NO);
            } else {
                saveobj = rulestoreService.get(new Qzstoreset(paramobj.getId()));
            }
            saveobj.setServername(paramobj.getServername());
            saveobj.setServertype(paramobj.getServertype());
            saveobj.setViewpath(paramobj.getViewpath());
            saveobj.setUseflag(paramobj.getUseflag());
            saveobj.setUsetype(paramobj.getUsetype());
            saveobj.setFtpaddress(paramobj.getFtpaddress());
            saveobj.setFtpusername(paramobj.getFtpusername());
            saveobj.setFtppassword(paramobj.getFtppassword());
            saveobj.setFtpport(paramobj.getFtpport());
            saveobj.setLocaladdress(paramobj.getLocaladdress());
            saveobj.setRemark(paramobj.getRemark());

            if(rulestoreService.save(saveobj)==true){
                json.put("code", 1);
                json.put("message", "保存成功");
            }
        } catch (Exception e) {

        }
        renderString(response, json, jsonCallBack);
    }

    /**
     * 账号删除
     * @param request
     * @param response
     * @param model
     * @param jsonCallBack
     */
    @RequestMapping("qzstoresetDeleteData.htm")
    @ResponseBody
    public void qzstoresetDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        try {
            String ids = WebUtil.getPoPropertyByRequest(request, "ids");
            if(!StringUtils.isEmpty(ids)){
                List<String> listids = StringUtils.stringSplitToList(ids,",");
                if(listids!= null && listids.size() > 0){
                    for(String id: listids){
                    	Qzstoreset delobj = rulestoreService.get(new Qzstoreset(id));
                        if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
                            delobj.setDeleteflag(Global.YES);
                            rulestoreService.save(delobj);
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
