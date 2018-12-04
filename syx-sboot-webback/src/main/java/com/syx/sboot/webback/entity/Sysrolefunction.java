package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysrolefunctionEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysrolefunction")
public class Sysrolefunction extends DataEntity<Sysrolefunction> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String roleid;
   @DbField
   private String functionid;
   @DbField
   private String appid;

   public Sysrolefunction() {
       super();
   }

   public Sysrolefunction(String id) {
       super(id);
   }

   public void setRoleid(String roleid){
       this.roleid = roleid;
   }

   public String getRoleid(){
       return this.roleid;
   }
   public void setFunctionid(String functionid){
       this.functionid = functionid;
   }

   public String getFunctionid(){
       return this.functionid;
   }
   public void setAppid(String appid){
       this.appid = appid;
   }

   public String getAppid(){
       return this.appid;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}