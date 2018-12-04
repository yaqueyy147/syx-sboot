package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysdeptEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysdept")
public class Sysdept extends DataEntity<Sysdept> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String parentid;
   @DbField
   private String depttype;
   @DbField
   private String deptno;
   @DbField
   private String deptname;
   @DbField
   private String managerid;
   @DbField
   private String tempmanagerids;
   @DbField
   private String supermanagerid;

   public Sysdept() {
       super();
   }

   public Sysdept(String id) {
       super(id);
   }

   public void setParentid(String parentid){
       this.parentid = parentid;
   }

   public String getParentid(){
       return this.parentid;
   }
   public void setDepttype(String depttype){
       this.depttype = depttype;
   }

   public String getDepttype(){
       return this.depttype;
   }
   public void setDeptno(String deptno){
       this.deptno = deptno;
   }

   public String getDeptno(){
       return this.deptno;
   }
   public void setDeptname(String deptname){
       this.deptname = deptname;
   }

   public String getDeptname(){
       return this.deptname;
   }
   public void setManagerid(String managerid){
       this.managerid = managerid;
   }

   public String getManagerid(){
       return this.managerid;
   }
   public void setTempmanagerids(String tempmanagerids){
       this.tempmanagerids = tempmanagerids;
   }

   public String getTempmanagerids(){
       return this.tempmanagerids;
   }
   public void setSupermanagerid(String supermanagerid){
       this.supermanagerid = supermanagerid;
   }

   public String getSupermanagerid(){
       return this.supermanagerid;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}