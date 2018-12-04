package com.syx.sboot.webback.entity;

import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysroleEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysrole")
public class Sysrole extends DataEntity<Sysrole> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String roleno;
   @DbField
   private String name;

   public Sysrole() {
       super();
   }

   public Sysrole(String id) {
       super(id);
   }

   public void setRoleno(String roleno){
       this.roleno = roleno;
   }

   public String getRoleno(){
       return this.roleno;
   }
   public void setName(String name){
       this.name = name;
   }

   public String getName(){
       return this.name;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}