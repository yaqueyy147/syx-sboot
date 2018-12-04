package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.access.SqlUtil;
import com.syx.sboot.common.access.TxSqlRunner;
import com.syx.sboot.common.access.TxSqlRunnerListener;
import com.syx.sboot.common.access.TxSqlRunnerUtil;
import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.json.JsonMapper;
import com.syx.sboot.common.utils.MD5Util02;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.common.utils.WebUtil;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.dto.TreeDto;
import com.syx.sboot.webback.entity.*;
import com.syx.sboot.webback.service.*;
import com.syx.sboot.webback.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/backstage/sys")
public class UserManageController extends AdminBaseController {
	String INDEX_PATH = "";


	@Autowired
	SysroleService sysroleService;

	@Autowired
	SysdicService sysdicService;

	@Autowired
	SysstaffService sysstaffService;
	
	@Autowired
	SysfunctionService sysfunctionService;
	
	@Autowired
	SysrolefunctionService sysrolefunctionService;

	@Autowired
	SysstaffdicService sysstaffdicService;

	// 数据权限
	@RequestMapping("userdatamanage.htm")
	public String userdatamanage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return INDEX_PATH + "userdatamanage";
	}

	// url
	@RequestMapping("usermanage.htm")
	public String usermanage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return INDEX_PATH + "usermanage";
	}

	@RequestMapping("staffroleset.htm")
	public String staffroleset(HttpServletRequest request, HttpServletResponse response, Model model) {
		String staffid = WebUtil.getPoPropertyByRequest(request, "staffid");
		StringBuilder sb = new StringBuilder();
		sb.append(" and staffid = ?");
		SqlQuery query = new SqlQuery();
		query.appendSql(sb.toString());
		query.addParam(staffid);
		List<Sysstaffrole> liststaffrole = SqlUtil.get("", "sysstaffrole", Sysstaffrole.class, query);
		StringBuilder staffroles = new StringBuilder();
		if (liststaffrole != null && liststaffrole.size() > 0) {
			boolean flag = false;
			for (Sysstaffrole item : liststaffrole) {
				if (flag) {
					staffroles.append(",");
				} else {
					flag = true;
				}
				staffroles.append(item.getRoleid());
			}
		}
		List<Sysrole> listroles = sysroleService.findList(new Sysrole());
		model.addAttribute("staffid", staffid);
		model.addAttribute("staffroles", staffroles.toString());
		model.addAttribute("listroles", JsonMapper.toJsonString(listroles).replace("\"", "'"));
		return INDEX_PATH + "staffroleset";
	}

	@RequestMapping("staffdicset.htm")
	public String staffdicset(HttpServletRequest request, HttpServletResponse response, Model model) {
		String staffid = WebUtil.getPoPropertyByRequest(request, "staffid");
		String dictype = WebUtil.getPoPropertyByRequest(request, "dictype");
		StringBuilder sb = new StringBuilder();
		sb.append(" and staffid='" + staffid + "'");
		sb.append(" and dictype='" + dictype + "'");
		List<Sysstaffdic> liststaffdic = sysstaffdicService.findList(new Sysstaffdic(), sb.toString());
		String staffdicvalue = "";
		String staffdictext = "";
		if (liststaffdic != null && liststaffdic.size() > 0) {
			staffdicvalue = liststaffdic.get(0).getDicvalue();
			staffdictext = liststaffdic.get(0).getDictext();
		}

		List<Sysdic> listdics = sysdicService.findList(new Sysdic(), " and catekey='" + dictype + "'");
		model.addAttribute("staffid", staffid);
		model.addAttribute("dictype", dictype);
		model.addAttribute("staffdicvalue", staffdicvalue);
		model.addAttribute("staffdictext", staffdictext);

		model.addAttribute("listdics", JsonMapper.toJsonString(listdics).replace("\"", "'"));
		return INDEX_PATH + "staffdicset";
	}

	@RequestMapping("rolemanage.htm")
	public String rolemanage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return INDEX_PATH + "rolemanage";
	}

	@RequestMapping("rolefunctionassign.htm")
	public String rolefunctionassign(HttpServletRequest request, HttpServletResponse response, Model model) {
		String roleid = WebUtil.getPoPropertyByRequest(request, "roleid");
		model.addAttribute("roleid", roleid);
		return INDEX_PATH + "rolefunctionassign";
	}
	// url结束

	// 用户管理

	@RequestMapping("queryStaffList.htm")
	@ResponseBody
	public void queryStaffList(HttpServletRequest request, HttpServletResponse response, Model model,
                               String jsonCallBack) {
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		Sysstaff paramobj = new Sysstaff();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		if (!StringUtils.isEmpty(paramobj.getName())) {
			sb.append(" and name like '%" + paramobj.getName() + "%'");
		}
		if (!StringUtils.isEmpty(paramobj.getDeptid())) {
			sb.append(" and deptid like '%" + paramobj.getDeptid() + "%'");
		}
		Page<Sysstaff> page = sysstaffService.findPageByCondition(new Page<Sysstaff>(request, response), paramobj,
				sb.toString());
		json.put("page", page);
		renderString(response, json, jsonCallBack);
	}

	// 保存用户相关信息
	@RequestMapping("staffSaveData.htm")
	@ResponseBody
	public void staffSaveData(HttpServletRequest request, HttpServletResponse response, Model model,
                              String jsonCallBack) {
		JSONObject json = new JSONObject();
		Sysstaff paramobj = new Sysstaff();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		Sysstaff saveobj = new Sysstaff();
		try {
			if (StringUtils.isEmpty(paramobj.getId())) {
				saveobj.setLoginflag(Global.YES);
				saveobj.setAdminstatus(Global.NO);
				saveobj.setIsNewRecord(true);
				saveobj.setCreateid(UserUtils.getUserid(request));
				saveobj.setCreatename(UserUtils.getUsername(request));
			} else {
				saveobj = sysstaffService.get(new Sysstaff(paramobj.getId()));
			}
			saveobj.setStaffno(paramobj.getStaffno());
			saveobj.setLoginname(paramobj.getLoginname());
			saveobj.setName(paramobj.getName());
			saveobj.setMobilephone(paramobj.getMobilephone());
			if (!StringUtils.isEmpty(paramobj.getLoginpwd())) {
				saveobj.setLoginpwd(MD5Util02.getMD5_32(paramobj.getLoginpwd()));
			}
			saveobj.setRemark(paramobj.getRemark());
			saveobj.setLoginflag(paramobj.getLoginflag());

			sysstaffService.save(saveobj);

			json.put("code", 1);
			json.put("message", "保存成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("staffDeleteData.htm")
	@ResponseBody
	public void staffDeleteData(HttpServletRequest request, HttpServletResponse response, Model model,
                                String jsonCallBack) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			if (!StringUtils.isEmpty(ids)) {
				List<String> listids = StringUtils.stringSplitToList(ids, ",");
				if (listids != null && listids.size() > 0) {
					for (String id : listids) {
						Sysstaff delobj = sysstaffService.get(new Sysstaff(id));
						if (delobj != null && !StringUtils.isEmpty(delobj.getId())) {
							delobj.setDeleteflag(Global.YES);
							sysstaffService.save(delobj);
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

	@RequestMapping("staffRoleSave.htm")
	@ResponseBody
	public void staffRoleSave(HttpServletRequest request, HttpServletResponse response, Model model,
                              String jsonCallBack) {
		final JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			final String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			final String staffid = WebUtil.getPoPropertyByRequest(request, "staffid");
			if (!StringUtils.isEmpty(staffid)) {
				TxSqlRunnerUtil.execute("", new TxSqlRunnerListener() {
					@Override
					public void onTxSqlRunnerReady(TxSqlRunner runner) throws Exception {
						SqlQuery sqlquery = new SqlQuery();
						sqlquery.appendSql("delete from sysstaffrole WHERE staffid = ?");
						sqlquery.addParam(staffid);
						SqlUtil.deleteBySqlQuery(runner, sqlquery);

						List<String> listids = StringUtils.stringSplitToList(ids, ",");
						if (listids != null && listids.size() > 0) {
							for (String item : listids) {
								Sysstaffrole saveobj = new Sysstaffrole();
								saveobj.setRoleid(item);
								saveobj.setStaffid(staffid);
								SqlUtil.insert(runner, "sysstaffrole", saveobj);
							}
						}
						json.put("code", 1);
						json.put("message", "保存成功");						
					}
				});


				
			}

		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("staffDicSave.htm")
	@ResponseBody
	public void staffDicSave(HttpServletRequest request, HttpServletResponse response, Model model,
                             String jsonCallBack) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			String staffid = WebUtil.getPoPropertyByRequest(request, "staffid");
			String dictype = WebUtil.getPoPropertyByRequest(request, "dictype");
			String idsText = WebUtil.getPoPropertyByRequest(request, "idsText");
			if (!StringUtils.isEmpty(staffid) && !StringUtils.isEmpty(dictype)) {
				Sysstaffdic saveobj = new Sysstaffdic();
				StringBuilder sb = new StringBuilder();
				sb.append(" and staffid='" + staffid + "'");
				sb.append(" and dictype='" + dictype + "'");
				List<Sysstaffdic> liststaffdic = sysstaffdicService.findList(new Sysstaffdic(), sb.toString());
				if (liststaffdic != null && liststaffdic.size() > 0) {
					saveobj = liststaffdic.get(0);
				} else {
					saveobj.setDictype(dictype);
					saveobj.setStaffid(staffid);
					saveobj.setIsNewRecord(true);
					saveobj.setCreateid(UserUtils.getUserid(request));
					saveobj.setCreatename(UserUtils.getUsername(request));
				}
				saveobj.setDicvalue(ids);
				saveobj.setDictext(idsText);
				sysstaffdicService.save(saveobj);

				json.put("code", 1);
				json.put("message", "保存成功");
			}

		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

	// 角色管理
	@RequestMapping("queryRoleList.htm")
	@ResponseBody
	public void queryRoleList(HttpServletRequest request, HttpServletResponse response, Model model,
                              String jsonCallBack) {
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		Sysrole paramobj = new Sysrole();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		if (!StringUtils.isEmpty(paramobj.getName())) {
			sb.append(" and name like '%" + paramobj.getName() + "%'");
		}
		Page<Sysrole> page = sysroleService.findPageByCondition(new Page<Sysrole>(request, response), paramobj,
				sb.toString());
		json.put("page", page);
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("roleSaveData.htm")
	@ResponseBody
	public void roleSaveData(HttpServletRequest request, HttpServletResponse response, Model model,
                             String jsonCallBack) {
		JSONObject json = new JSONObject();
		Sysrole paramobj = new Sysrole();
		WebUtil.setPoPropertyByRequest(paramobj, request);
		Sysrole saveobj = new Sysrole();
		try {
			if (StringUtils.isEmpty(paramobj.getId())) {
				saveobj.setIsNewRecord(true);
				saveobj.setCreateid(UserUtils.getUserid(request));
				saveobj.setCreatename(UserUtils.getUsername(request));
			} else {
				saveobj = sysroleService.get(new Sysrole(paramobj.getId()));
			}

			saveobj.setName(paramobj.getName());
			saveobj.setRemark(paramobj.getRemark());
			sysroleService.save(saveobj);

			json.put("code", 1);
			json.put("message", "保存成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

	@RequestMapping("roleDeleteData.htm")
	@ResponseBody
	public void roleDeleteData(HttpServletRequest request, HttpServletResponse response, Model model,
                               String jsonCallBack) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			if (!StringUtils.isEmpty(ids)) {
				List<String> listids = StringUtils.stringSplitToList(ids, ",");
				if (listids != null && listids.size() > 0) {
					for (String id : listids) {
						Sysrole delobj = sysroleService.get(new Sysrole(id));
						if (delobj != null && !StringUtils.isEmpty(delobj.getId())) {
							delobj.setDeleteflag(Global.YES);
							sysroleService.save(delobj);
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

	@RequestMapping("roleFunctionData.htm")
	@ResponseBody
	public Object roleFunctionData(HttpServletRequest request, HttpServletResponse response, Model model,
                                 String jsonCallBack) {
		JSONObject json = new JSONObject();
		String roleid = WebUtil.getPoPropertyByRequest(request, "roleid");
		List<TreeDto> listdata = new ArrayList<TreeDto>();
		if (!StringUtils.isEmpty(roleid)) {
			List<Sysfunction> listpre = sysfunctionService.findList(new Sysfunction());
			Sysfunction search = new Sysfunction();
			search.setVar01(roleid);
			List<Sysfunction> ownlist = new ArrayList<Sysfunction>();
			
			SqlQuery sqlquery = new SqlQuery();
			sqlquery.appendSql(" SELECT DISTINCT a.*");
			sqlquery.appendSql(" FROM sysfunction a");
			sqlquery.appendSql(" LEFT JOIN sysfunction p ON p.id = a.parentid");
			sqlquery.appendSql(" JOIN sysrolefunction rm ON rm.functionid = a.id");
			sqlquery.appendSql(" JOIN sysrole r ON r.id = rm.roleid AND r.id=?");
			sqlquery.appendSql(" WHERE a.deleteflag = '0' AND r.deleteflag = '0'"); 
			sqlquery.appendSql("ORDER BY a.orderno");
			sqlquery.addParam(roleid);
			ownlist = SqlUtil.getByOriginSQL("", Sysfunction.class, sqlquery);

			if (listpre != null && listpre.size() > 0) {
				for (Sysfunction pre : listpre) {
					boolean checkhave = false;
					for (Sysfunction own : ownlist) {
						if (StringUtils.equals(pre.getId(), own.getId())) {
							checkhave = true;
						}
					}
					Map<String, Object> attributes = new HashMap<String, Object>();
					TreeDto dto = new TreeDto();
					dto.setId(pre.getId());
					dto.setpId(pre.getParentid());
					dto.setName(pre.getFunctionname());
					attributes.put("url", pre.getLinkurl());
					dto.setAttributes(attributes);
					dto.setChecked(checkhave);
					listdata.add(dto);
				}
			}
		}
		json.put("result", listdata);
		return listdata;
	}

	@RequestMapping("roleFunctionSave.htm")
	@ResponseBody
	public void roleFunctionSave(final HttpServletRequest request, HttpServletResponse response, Model model,
								 String jsonCallBack) {
		final JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "保存失败");
		try {
			final String ids = WebUtil.getPoPropertyByRequest(request, "ids");
			final String roleid = WebUtil.getPoPropertyByRequest(request, "roleid");
			if (!StringUtils.isEmpty(roleid)) {
				TxSqlRunnerUtil.execute("", new TxSqlRunnerListener() {
					@Override
					public void onTxSqlRunnerReady(TxSqlRunner runner) throws Exception {
						SqlUtil.deleteByColumn(runner, "sysrolefunction", "roleid", roleid);
						List<String> listids = StringUtils.stringSplitToList(ids, ",");
						if (listids != null && listids.size() > 0) {
							for (String item : listids) {
								Sysrolefunction saveobj = new Sysrolefunction();
								saveobj.setIsNewRecord(true);
								saveobj.setCreateid(UserUtils.getUserid(request));
								saveobj.setCreatename(UserUtils.getUsername(request));
								saveobj.setRoleid(roleid);
								saveobj.setFunctionid(item);
								SqlUtil.insert(runner, "sysrolefunction", saveobj);
							}
						}
						json.put("code", 1);
						json.put("message", "保存成功");					
					}
				});
				
				
			}

		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "保存失败");
		}
		renderString(response, json, jsonCallBack);
	}

}
