package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysparamEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysparam")
public class Sysparam extends DataEntity<Sysparam> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String paramkey;
   @DbField
   private String paramvalue;
   @DbField
   private String description;

   public Sysparam() {
       super();
   }

   public Sysparam(String id) {
       super(id);
   }

   public void setParamkey(String paramkey){
       this.paramkey = paramkey;
   }

   public String getParamkey(){
       return this.paramkey;
   }
   public void setParamvalue(String paramvalue){
       this.paramvalue = paramvalue;
   }

   public String getParamvalue(){
       return this.paramvalue;
   }
   public void setDescription(String description){
       this.description = description;
   }

   public String getDescription(){
       return this.description;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}