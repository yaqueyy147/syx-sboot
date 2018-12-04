
package com.syx.sboot.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syx.sboot.common.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 数据Entity类
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	@DbField
	protected String remark; // 备注
	@DbField
	protected String createid; // 创建人ID
	@DbField
	protected String createname; // 创建人
	@DbField
	protected Date createdate; // vmContext.put("tableName", tableMeta.getTableName());创建日期

	@DbField
	protected String updatename; // 修改人
	@DbField
	protected Date updatedate; // 更新日期
	@DbField
	protected String deleteflag; // 删除标记（0：正常；1：删除；2：审核）

	// 扩展字段
	@DbField
	protected String var01; // 扩展字段01
	@DbField
	protected String var02; // 扩展字段02
	@DbField
	protected String var03; // 扩展字段03
	@DbField
	protected String var04; // 扩展字段04
	@DbField
	protected String var05; // 扩展字段05

	public DataEntity() {
		super();
		this.deleteflag = DEL_FLAG_NORMAL;
	}

	public DataEntity(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (this.isGenKey && StringUtils.isEmpty(this.id)) {
			setId(IdGen.uuid());
		}
		this.updatedate = new Date();
		this.createdate = this.updatedate;
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate() {
		this.updatedate = new Date();
	}

	@Length(min = 0, max = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Length(min = 0, max = 255)
	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	@Length(min = 0, max = 255)
	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	@Length(min = 0, max = 255)
	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@JsonIgnore
	@Length(min = 1, max = 1)
	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	@Length(min = 0, max = 255)
	public String getVar01() {
		return var01;
	}

	public void setVar01(String var01) {
		this.var01 = var01;
	}

	@Length(min = 0, max = 255)
	public String getVar02() {
		return var02;
	}

	public void setVar02(String var02) {
		this.var02 = var02;
	}

	@Length(min = 0, max = 255)
	public String getVar03() {
		return var03;
	}

	public void setVar03(String var03) {
		this.var03 = var03;
	}

	@Length(min = 0, max = 255)
	public String getVar04() {
		return var04;
	}

	public void setVar04(String var04) {
		this.var04 = var04;
	}

	@Length(min = 0, max = 255)
	public String getVar05() {
		return var05;
	}

	public void setVar05(String var05) {
		this.var05 = var05;
	}

}
