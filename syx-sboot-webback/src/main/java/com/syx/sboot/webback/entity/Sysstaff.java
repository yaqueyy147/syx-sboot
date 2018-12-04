package com.syx.sboot.webback.entity;

import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

import java.util.Date;

/**
 * SysstaffEntity
 * @author hua.yang
 * @date 2017-07-21
 */
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysstaff")
public class Sysstaff extends DataEntity<Sysstaff> {
	private static final long serialVersionUID = 1L;

	@DbField
	private String loginname;
	@DbField
	private String loginpwd;
	@DbField
	private String staffno;
	@DbField
	private String name;
	@DbField
	private String deptid;
	@DbField
	private String deptname;
	@DbField
	private String sex;
	@DbField
	private String address;
	@DbField
	private String email;
	@DbField
	private String hostip;
	@DbField
	private String mobilephone;
	@DbField
	private String connectphone;
	@DbField
	private String adminstatus;
	@DbField
	private String loginip;
	@DbField
	private Date logindate;
	@DbField
	private String loginflag;

	public Sysstaff() {
		super();
	}

	public Sysstaff(String id) {
		super(id);
	}

	public void setLoginname(String loginname){
		this.loginname = loginname;
	}

	public String getLoginname(){
		return this.loginname;
	}	   
	public void setLoginpwd(String loginpwd){
		this.loginpwd = loginpwd;
	}

	public String getLoginpwd(){
		return this.loginpwd;
	}	   
	public void setStaffno(String staffno){
		this.staffno = staffno;
	}

	public String getStaffno(){
		return this.staffno;
	}	   
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}	   
	public void setDeptid(String deptid){
		this.deptid = deptid;
	}

	public String getDeptid(){
		return this.deptid;
	}	   
	public void setDeptname(String deptname){
		this.deptname = deptname;
	}

	public String getDeptname(){
		return this.deptname;
	}	   
	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return this.sex;
	}	   
	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}	   

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}	   
	public void setHostip(String hostip){
		this.hostip = hostip;
	}

	public String getHostip(){
		return this.hostip;
	}	   
	public void setMobilephone(String mobilephone){
		this.mobilephone = mobilephone;
	}

	public String getMobilephone(){
		return this.mobilephone;
	}	   
	public void setConnectphone(String connectphone){
		this.connectphone = connectphone;
	}

	public String getConnectphone(){
		return this.connectphone;
	}	   
	public void setAdminstatus(String adminstatus){
		this.adminstatus = adminstatus;
	}

	public String getAdminstatus(){
		return this.adminstatus;
	}	   
	public void setLoginip(String loginip){
		this.loginip = loginip;
	}

	public String getLoginip(){
		return this.loginip;
	}	   
	public void setLogindate(Date logindate){
		this.logindate = logindate;
	}

	public Date getLogindate(){
		return this.logindate;
	}	   
	public void setLoginflag(String loginflag){
		this.loginflag = loginflag;
	}

	public String getLoginflag(){
		return this.loginflag;
	}	   

	public Object objectKeyValue() {
		return this.getId();
	}
}