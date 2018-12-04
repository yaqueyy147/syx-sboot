package com.syx.sboot.common.entity;

@DbCodeField(value = "")
@DbTableField(value="sysdatasource")
public class SysDataSource extends DataEntity<SysDataSource> {
	private static final long serialVersionUID = 1L;
	@DbField
	private String dscode;
	@DbField
	private String dsname;
	@DbField
	private String dsdriver;
	@DbField
	private String dsurl;
	@DbField
	private String dsusername;
	@DbField
	private String dspassword;
	@DbField
	private Integer dsmaxactive;
	@DbField
	private Integer dsmaxidle;
	@DbField
	private Integer dscheckouttime;
	@DbField
	private Integer dswaittime;
	@DbField
	private Integer dspingenabled;
	@DbField
	private String dspingquery;
	@DbField
	private Integer dspingtime;
	@DbField
	private Integer dsasorg;
	@DbField
	private String dsremark;
	@DbField
	private Integer dataversion;
	@DbField
	private Integer datavalid;

	public SysDataSource() {
	}

	public SysDataSource(String id) {
		super(id);
	}

	public String getDscode() {
		return dscode;
	}

	public void setDscode(String dscode) {
		this.dscode = dscode;
	}

	public String getDsname() {
		return dsname;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public String getDsdriver() {
		return dsdriver;
	}

	public void setDsdriver(String dsdriver) {
		this.dsdriver = dsdriver;
	}

	public String getDsurl() {
		return dsurl;
	}

	public void setDsurl(String dsurl) {
		this.dsurl = dsurl;
	}

	public String getDsusername() {
		return dsusername;
	}

	public void setDsusername(String dsusername) {
		this.dsusername = dsusername;
	}

	public String getDspassword() {
		return dspassword;
	}

	public void setDspassword(String dspassword) {
		this.dspassword = dspassword;
	}

	public Integer getDsmaxactive() {
		return dsmaxactive;
	}

	public void setDsmaxactive(Integer dsmaxactive) {
		this.dsmaxactive = dsmaxactive;
	}

	public Integer getDsmaxidle() {
		return dsmaxidle;
	}

	public void setDsmaxidle(Integer dsmaxidle) {
		this.dsmaxidle = dsmaxidle;
	}

	public Integer getDscheckouttime() {
		return dscheckouttime;
	}

	public void setDscheckouttime(Integer dscheckouttime) {
		this.dscheckouttime = dscheckouttime;
	}

	public Integer getDswaittime() {
		return dswaittime;
	}

	public void setDswaittime(Integer dswaittime) {
		this.dswaittime = dswaittime;
	}

	public Integer getDspingenabled() {
		return dspingenabled;
	}

	public void setDspingenabled(Integer dspingenabled) {
		this.dspingenabled = dspingenabled;
	}

	public String getDspingquery() {
		return dspingquery;
	}

	public void setDspingquery(String dspingquery) {
		this.dspingquery = dspingquery;
	}

	public Integer getDspingtime() {
		return dspingtime;
	}

	public void setDspingtime(Integer dspingtime) {
		this.dspingtime = dspingtime;
	}

	public Integer getDsasorg() {
		return dsasorg;
	}

	public void setDsasorg(Integer dsasorg) {
		this.dsasorg = dsasorg;
	}

	public String getDsremark() {
		return dsremark;
	}

	public void setDsremark(String dsremark) {
		this.dsremark = dsremark;
	}

	public Integer getDataversion() {
		return dataversion;
	}

	public void setDataversion(Integer dataversion) {
		this.dataversion = dataversion;
	}

	public Integer getDatavalid() {
		return datavalid;
	}

	public void setDatavalid(Integer datavalid) {
		this.datavalid = datavalid;
	}

	public Object objectKeyValue() {
		return this.getId();
	}
}
