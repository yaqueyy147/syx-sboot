package com.syx.sboot.webback.controller;


import com.alibaba.fastjson.JSONObject;
import com.syx.sboot.common.entity.Global;
import com.syx.sboot.common.utils.StringUtils;
import com.syx.sboot.webback.AdminBaseController;
import com.syx.sboot.webback.entity.Sysstaff;
import com.syx.sboot.webback.service.extend.SysUserService;
import com.syx.sboot.webback.utils.CookieUtil;
import com.syx.sboot.webback.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/admin")
public class IndexController extends AdminBaseController {
	String INDEX_PATH = "/";

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("introduce.htm")
	public String introduce(HttpServletRequest request, HttpServletResponse response, Model model) {
		return INDEX_PATH + "introduce";
	}

	
	@RequestMapping("login.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Object mscode = request.getParameter("mscode");
		if(mscode!= null && !StringUtils.isEmpty(mscode.toString()))
		{
			model.addAttribute("message", mscode.toString());
		}
		return "login";
	}

	@RequestMapping(value = "logincheck.htm")
	public RedirectView logincheck(@ModelAttribute("userName") String userName, @ModelAttribute("password") String password, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("message", "登录失败！");

		try {
			if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
				Sysstaff user = sysUserService.getUserByLoginName(userName, password);
				if (user != null) {
					if (Global.NO.equals(user.getLoginflag())){
						json.put("code", 0);
						json.put("message", "该账号已禁止登录");
					}
					else
					{
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("id",user.getId());
						jsonObject.put("name",user.getName());
						jsonObject.put("loginname",user.getLoginname());
						jsonObject.put("loginpwd",user.getLoginpwd());
						CookieUtil.addCookie(UserUtils.COOKIE_NAME,jsonObject.toJSONString(),res);
						json.put("code", 1);
						json.put("message", "登录成功");
						return new RedirectView("/admin/backstage/home.htm");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new RedirectView("/admin/login.htm");
	}

//	@RequestMapping(value = "logincheck.htm")
//	@ResponseBody
//	public void logincheck(@ModelAttribute("userName") String userName, @ModelAttribute("password") String password, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
//		JSONObject json = new JSONObject();
//		json.put("code", 0);
//		json.put("message", "登录失败！");
//
//		try {
//			if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
//				Sysstaff user = sysUserService.getUserByLoginName(userName, password);
//				if (user != null) {
//					if (Global.NO.equals(user.getLoginflag())){
//						json.put("code", 0);
//						json.put("message", "该账号已禁止登录");
//					}
//					else
//					{
////						SessionLocal local = new SessionLocal();
////						local.setLastDate(new Date());
////						local.setTruename(user.getName());
////						local.setUserid(user.getId());
////						local.setUsername(user.getLoginname());
////						local.setUserphone(user.getMobilephone());
////
////						SessionLocalManager.setSessionLocal(local);
//						JSONObject jsonObject = new JSONObject();
//						jsonObject.put("id",user.getId());
//						jsonObject.put("name",user.getName());
//						jsonObject.put("loginname",user.getLoginname());
//						jsonObject.put("loginpwd",user.getLoginpwd());
//						CookieUtil.addCookie(UserUtils.COOKIE_NAME,jsonObject.toJSONString(),res);
//						json.put("code", 1);
//						json.put("message", "登录成功");
//					}
//				}
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		renderString(res,json);
//	}
	
	
	// 报错
	@RequestMapping("error.htm")
	public String error(HttpServletRequest request, HttpServletResponse response, Model model) {
		return INDEX_PATH + "error";
	}
	// 用户登出
	@RequestMapping("loginout.htm")
	public String loginout(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			CookieUtil.destroyCookieFromName(response,request,UserUtils.COOKIE_NAME);
//			SessionLocalManager.destroy();
		} catch (Exception e) {
			;
		}

		return "redirect:" + "/admin/login.htm";
	}
	
}
