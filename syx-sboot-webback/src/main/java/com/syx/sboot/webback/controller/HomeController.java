package com.syx.sboot.webback.controller;

import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.access.SqlUtil;
import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Sysfunction;
import com.syx.sboot.webback.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/backstage")
public class HomeController extends AdminBaseController {
	String INDEX_PATH = "/";
	

	@RequestMapping("home.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject jsonObject = UserUtils.getCookie(request);
		model.addAttribute("user",jsonObject);
		return INDEX_PATH + "home";
	}
	
	@RequestMapping(value = "getUserFunctions.htm")
	@ResponseBody
	public void getUserFunctions(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		
		String appids = "'1'";
		SqlQuery query = new SqlQuery();
		if(UserUtils.getUsername(req).equals("admin")){
			query.appendSql("SELECT DISTINCT a.*");
			query.appendSql(" FROM sysfunction a");
			query.appendSql(" LEFT JOIN sysfunction p ON p.id = a.parentid");
			query.appendSql(" WHERE a.deleteflag = '0' and a.appid in( "+appids+" )");
			query.appendSql(" ORDER BY a.orderno");
		}else{
			query.appendSql("SELECT DISTINCT a.*");
			query.appendSql(" FROM sysfunction a");
			query.appendSql(" LEFT JOIN sysfunction p ON p.id = a.parentid");
			query.appendSql(" JOIN sysrolefunction rm ON rm.functionid = a.id");
			query.appendSql(" JOIN sysrole r ON r.id = rm.roleid");
			query.appendSql(" JOIN sysstaffrole ur ON ur.roleid = r.id");
			query.appendSql(" JOIN sysstaff u ON u.id = ur.staffid AND u.id = ? AND a.appid in( "+appids+" )");
			query.appendSql(" WHERE a.deleteflag = '0' AND r.deleteflag = '0' AND u.deleteflag = '0'");
			query.appendSql(" ORDER BY a.orderno");
			query.addParam(UserUtils.getUserid(req));
		}

				
		List<Sysfunction> menuList = SqlUtil.getByOriginSQL("", Sysfunction.class, query);
		json.put("result", menuList);			
		renderString(res,json);		
	}
}
