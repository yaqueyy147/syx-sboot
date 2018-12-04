package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.access.SqlUtil;
import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.dto.TreeDto;
import com.syx.sboot.webback.entity.Sysdept;
import com.syx.sboot.webback.entity.Sysstaff;
import com.syx.sboot.webback.service.SysdeptService;
import com.syx.sboot.webback.service.SysstaffService;
import com.syx.sboot.webback.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyx on 2017/7/25 0025.
 */
@Controller
@RequestMapping(value = "/admin/backstage/sys")
public class DeptManageController extends AdminBaseController {
    private static final String INDEX_PATH = "";

    @Autowired
    private SysdeptService sysdeptService;
    @Autowired
    private SysstaffService sysstaffService;

    @RequestMapping(value = "/deptmanage.htm")
    public ModelAndView deptmanage(){
        return new ModelAndView(INDEX_PATH + "deptmanage");
    }

    @RequestMapping("queryDeptList.htm")
    @ResponseBody
    public void queryDeptList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        Sysdept paramobj = new Sysdept();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        if(!StringUtils.isEmpty(paramobj.getDeptname())){
            sb.append(" and deptname like '%"+paramobj.getDeptname()+"%'");
        }
        if(!StringUtils.isEmpty(paramobj.getDeptno())){
            sb.append(" and deptno='"+paramobj.getDeptno()+"'");
        }
        //根据查询条件查询机构信息
        List<Sysdept> list = sysdeptService.findList(new Sysdept(),sb.toString());
        List<TreeDto> treeData = new ArrayList<TreeDto>();
        for (Sysdept dept : list) {
            //设置机构树需要的参数
            TreeDto dto = new TreeDto();
            dto.setId(dept.getId());
            dto.setpId(dept.getParentid());
            dto.setName(dept.getDeptname());
            dto.setOpen(true);
            dto.setText(dept.getDeptno());
            treeData.add(dto);
        }
        json.put("deptList", list);
        json.put("treeData",treeData);
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("deptSaveData.htm")
    @ResponseBody
    public void deptSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        Sysdept paramobj = new Sysdept();
        WebUtil.setPoPropertyByRequest(paramobj, request);
        Sysdept saveobj = new Sysdept();
        try {
            if (StringUtils.isEmpty(paramobj.getId())) {
                saveobj.setIsNewRecord(true);
                saveobj.setCreateid(UserUtils.getUserid(request));
                saveobj.setCreatename(UserUtils.getUsername(request));
            } else {
                saveobj = sysdeptService.get(new Sysdept(paramobj.getId()));
            }

            saveobj.setParentid(paramobj.getParentid());
            saveobj.setDepttype(paramobj.getDepttype());
            saveobj.setDeptno(paramobj.getDeptno());
            saveobj.setDeptname(paramobj.getDeptname());
            saveobj.setManagerid(paramobj.getManagerid());
            saveobj.setTempmanagerids(paramobj.getTempmanagerids());
            saveobj.setSupermanagerid(paramobj.getSupermanagerid());
            saveobj.setRemark(paramobj.getRemark());
            saveobj.setVar01(paramobj.getVar01());
            saveobj.setVar02(paramobj.getVar02());
            saveobj.setVar03(paramobj.getVar03());
            saveobj.setVar04(paramobj.getVar04());
            saveobj.setVar05(paramobj.getVar05());
            sysdeptService.save(saveobj);

            json.put("code", 1);
            json.put("message", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 0);
            json.put("message", "保存失败");
        }
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping("deptDeleteData.htm")
    @ResponseBody
    public void deptDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "保存失败");
        try {
            String ids = WebUtil.getPoPropertyByRequest(request, "ids");
            if(!StringUtils.isEmpty(ids)){
                List<String> listids = StringUtils.stringSplitToList(ids,",");
                if(listids!= null && listids.size() > 0){
                    for(String id: listids){
                        Sysdept delobj = sysdeptService.get(new Sysdept(id));
                        if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
                            delobj.setDeleteflag(Global.YES);
                            sysdeptService.save(delobj);
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

    @RequestMapping("queryStaffList02.htm")
    @ResponseBody
    public void queryStaffList02(HttpServletRequest request, HttpServletResponse response, Model model,
                                 String jsonCallBack) {
        JSONObject json = new JSONObject();

//        SqlQuery query = new SqlQuery();
//        query.appendSql("select a.*,b.deptname from ");
//        query.appendSql(" sysstaff a");
//        query.appendSql(" left join sysdept b on a.deptid=b.id");
//        query.appendSql(" where a.deleteflag='0' and b.deleteflag='0'");
//
//        List<Map<String,Object>> list = SqlUtil.getByOriginSQL("", Map.class ,query);

        List<Sysstaff> list = sysstaffService.findList(new Sysstaff(),"order by createdate desc");

        json.put("userList", list);
        renderString(response, json, jsonCallBack);
    }

    @RequestMapping(value = "/setUserDept.htm")
    @ResponseBody
    public void setUserDept(HttpServletRequest request, HttpServletResponse response, String jsonCallBack){
        JSONObject json = new JSONObject();

        String ids = WebUtil.getPoPropertyByRequest(request,"ids");
        String deptid = WebUtil.getPoPropertyByRequest(request,"deptid");
        String deptname = WebUtil.getPoPropertyByRequest(request,"deptname");

        try{
            SqlQuery query = new SqlQuery();

            //先将该机构原有用户的机构设为空
            query.appendSql("update sysstaff set deptid='',deptname='' ");
            query.appendSql(" where deptid=?");
            query.addParam(deptid);
            SqlUtil.updateBySqlQuery("",query);

            //再将传入的用户设置机构
            query = new SqlQuery();
            query.appendSql("update sysstaff set deptid=?,deptname=? ");
            query.appendSql(" where id in (" + ids + ")");
            query.addParam(deptid);
            query.addParam(deptname);
            int i = SqlUtil.updateBySqlQuery("",query);
            json.put("code", i);
        }catch (Exception e){
            json.put("code", -1);
        }

        renderString(response, json, jsonCallBack);
    }

    @RequestMapping(value = "/deptUserDel.htm")
    @ResponseBody
    public void deptUserDel(HttpServletRequest request, HttpServletResponse response, String jsonCallBack){
        JSONObject json = new JSONObject();

        String ids = WebUtil.getPoPropertyByRequest(request,"ids");

        SqlQuery query = new SqlQuery();
        query.appendSql("update sysstaff set deptid='' ");
        query.appendSql(" where id in (" + ids + ")");

        try{
            int i = SqlUtil.updateBySqlQuery("",query);
            json.put("code", i);
        }catch (Exception e){
            json.put("code", -1);
        }

        renderString(response, json, jsonCallBack);
    }

}
