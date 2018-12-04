package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysdiccateEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysdiccate")
public class Sysdiccate extends DataEntity<Sysdiccate> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String catekey;
   @DbField
   private String catename;

   public Sysdiccate() {
       super();
   }

   public Sysdiccate(String id) {
       super(id);
   }

   public void setCatekey(String catekey){
       this.catekey = catekey;
   }

   public String getCatekey(){
       return this.catekey;
   }
   public void setCatename(String catename){
       this.catename = catename;
   }

   public String getCatename(){
       return this.catename;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}