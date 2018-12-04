package com.syx.sboot.webback.entity;

import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

import java.io.Serializable;

/**
* SysstaffroleEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysstaffrole")
public class Sysstaffrole implements Serializable {
   private static final long serialVersionUID = 1L;

   @DbField
   private String staffid;
   @DbField
   private String roleid;

   public Sysstaffrole() {
       super();
   }


   public void setStaffid(String staffid){
       this.staffid = staffid;
   }

   public String getStaffid(){
       return this.staffid;
   }
   public void setRoleid(String roleid){
       this.roleid = roleid;
   }

   public String getRoleid(){
       return this.roleid;
   }
}