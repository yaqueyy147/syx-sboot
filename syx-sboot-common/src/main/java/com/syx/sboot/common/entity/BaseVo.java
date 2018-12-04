
package com.syx.sboot.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据Entity类
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
public abstract class BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // ID
	private String remark; // 备注
	private String createid; // 创建人ID
	private String createname; // 创建人
	private Date createdate; // vmContext.put("tableName",
								// tableMeta.getTableName());创建日期
	private String updatename; // 修改人
	private Date updatedate; // 更新日期
	private String deleteflag; // 删除标记（0：正常；1：删除；2：审核）
	private String var01; // 扩展字段01
	private String var02; // 扩展字段02
	private String var03; // 扩展字段03
	private String var04; // 扩展字段04
	private String var05; // 扩展字段05

	public BaseVo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getVar01() {
		return var01;
	}

	public void setVar01(String var01) {
		this.var01 = var01;
	}

	public String getVar02() {
		return var02;
	}

	public void setVar02(String var02) {
		this.var02 = var02;
	}

	public String getVar03() {
		return var03;
	}

	public void setVar03(String var03) {
		this.var03 = var03;
	}

	public String getVar04() {
		return var04;
	}

	public void setVar04(String var04) {
		this.var04 = var04;
	}

	public String getVar05() {
		return var05;
	}

	public void setVar05(String var05) {
		this.var05 = var05;
	}
}
