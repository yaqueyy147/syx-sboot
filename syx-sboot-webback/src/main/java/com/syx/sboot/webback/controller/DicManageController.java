package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Sysdic;
import com.syx.sboot.webback.entity.Sysdiccate;
import com.syx.sboot.webback.service.SysdicService;
import com.syx.sboot.webback.service.SysdiccateService;
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
public class DicManageController extends AdminBaseController {
	String INDEX_PATH = "";
	
	@Autowired
	SysdicService sysdicService;

	@Autowired
	private SysdiccateService sysdiccateService;

	@RequestMapping(value = "/diccatemanage.htm")
	public ModelAndView sysdic(){
		return new ModelAndView(INDEX_PATH + "sysdic");
	}

	//url结束
	@RequestMapping("queryDicList.htm")
	@ResponseBody
	public void queryDicList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		Sysdic paramobj = new Sysdic();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		if(!StringUtils.isEmpty(paramobj.getCateid())){
			sb.append(" and cateid='"+paramobj.getCateid()+"'");
		}
		if(!StringUtils.isEmpty(paramobj.getCatekey())){
			sb.append(" and catekey='"+paramobj.getCatekey()+"'");
		}
		if(!StringUtils.isEmpty(paramobj.getDicvalue())){
			sb.append(" and dicvalue='"+paramobj.getDicvalue()+"'");
		}
		sb.append("order by createdate desc");
		Page<Sysdic> page = sysdicService.findPageByCondition(new Page<Sysdic>(request, response), paramobj, sb.toString());
		json.put("page", page);
		renderString(response, json, jsonCallBack);
	}
	
	//url结束
	@RequestMapping("queryDicList02.htm")
	@ResponseBody
	public void queryDicList02(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		StringBuilder sb = new StringBuilder();
		Sysdic paramobj = new Sysdic();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		if(!StringUtils.isEmpty(paramobj.getCatekey())){
			sb.append(" and catekey='"+paramobj.getCatekey()+"'");
		}else{
			sb.append(" and 1=0 ");
		}
		if(!StringUtils.isEmpty(paramobj.getDicvalue())){
			sb.append(" and a.dicvalue='"+paramobj.getDicvalue()+"'");
		}
		sb.append("order by createdate desc");
		List<Sysdic> list = sysdicService.findList(paramobj, sb.toString());
		renderString(response, list, jsonCallBack);
	}
	
	@RequestMapping("dicSaveData.htm")
	@ResponseBody
	public void roleSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		Sysdic paramobj = new Sysdic();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		Sysdic saveobj = new Sysdic();
		try {
			if (StringUtils.isEmpty(paramobj.getId())) {
				saveobj.setIsNewRecord(true);
				saveobj.setCreateid(UserUtils.getUserid(request));
				saveobj.setCreatename(UserUtils.getUsername(request));
			} else {
				saveobj = sysdicService.get(new Sysdic(paramobj.getId()));
			}

			saveobj.setCateid(paramobj.getCateid());
			saveobj.setCatekey(paramobj.getCatekey());
			saveobj.setParentid(paramobj.getParentid());
			saveobj.setChecktree(paramobj.getChecktree());
			saveobj.setDickey(paramobj.getDickey());
			saveobj.setDicvalue(paramobj.getDicvalue());
			saveobj.setMinvalue(paramobj.getMinvalue());
			saveobj.setMaxvalue(paramobj.getMaxvalue());
			saveobj.setVar01(paramobj.getVar01());
			saveobj.setVar02(paramobj.getVar02());
			saveobj.setVar03(paramobj.getVar03());
			saveobj.setVar04(paramobj.getVar04());
			saveobj.setVar05(paramobj.getVar05());
			saveobj.setVar06(paramobj.getVar06());
			saveobj.setVar07(paramobj.getVar07());
			saveobj.setVar08(paramobj.getVar08());
			saveobj.setVar09(paramobj.getVar09());
			saveobj.setVar10(paramobj.getVar10());
			sysdicService.save(saveobj);

			json.put("code", 1);
			json.put("message", "保存成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}
	
	@RequestMapping("dicDeleteData.htm")
	@ResponseBody
	public void roleDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			if(!StringUtils.isEmpty(ids)){
				List<String> listids = StringUtils.stringSplitToList(ids,",");
				if(listids!= null && listids.size() > 0){
					for(String id: listids){
						Sysdic delobj = sysdicService.get(new Sysdic(id));
						if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
							delobj.setDeleteflag(Global.YES);
							sysdicService.save(delobj);	
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


	//url结束
	@RequestMapping("queryDicCateList.htm")
	@ResponseBody
	public void queryDicCateList(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		Sysdiccate paramobj = new Sysdiccate();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		if(!StringUtils.isEmpty(paramobj.getCatekey())){
			sb.append(" and catekey='"+paramobj.getCatekey()+"'");
		}
		if(!StringUtils.isEmpty(paramobj.getCatename())){
			sb.append(" and catename='"+paramobj.getCatename()+"'");
		}
		sb.append("order by createdate desc");
		Page<Sysdiccate> page = sysdiccateService.findPageByCondition(new Page<Sysdiccate>(request, response), paramobj, sb.toString());
		json.put("page", page);
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("diccateSaveData.htm")
	@ResponseBody
	public void diccateSaveData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		Sysdiccate paramobj = new Sysdiccate();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		Sysdiccate saveobj = new Sysdiccate();
		try {
			if (StringUtils.isEmpty(paramobj.getId())) {
				saveobj.setIsNewRecord(true);
				saveobj.setCreateid(UserUtils.getUserid(request));
				saveobj.setCreatename(UserUtils.getUsername(request));
			} else {
				saveobj = sysdiccateService.get(new Sysdiccate(paramobj.getId()));
			}

			saveobj.setCatekey(paramobj.getCatekey());
			saveobj.setCatename(paramobj.getCatename());
			saveobj.setRemark(paramobj.getRemark());
			saveobj.setVar01(paramobj.getVar01());
			saveobj.setVar02(paramobj.getVar02());
			saveobj.setVar03(paramobj.getVar03());
			saveobj.setVar04(paramobj.getVar04());
			saveobj.setVar05(paramobj.getVar05());
			sysdiccateService.save(saveobj);

			json.put("code", 1);
			json.put("message", "保存成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("diccateDeleteData.htm")
	@ResponseBody
	public void diccateDeleteData(HttpServletRequest request, HttpServletResponse response, Model model, String jsonCallBack) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			if(!StringUtils.isEmpty(ids)){
				List<String> listids = StringUtils.stringSplitToList(ids,",");
				if(listids!= null && listids.size() > 0){
					for(String id: listids){
						Sysdiccate delobj = sysdiccateService.get(new Sysdiccate(id));
						if(delobj!= null && !StringUtils.isEmpty(delobj.getId())){
							delobj.setDeleteflag(Global.YES);
							sysdiccateService.save(delobj);
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
