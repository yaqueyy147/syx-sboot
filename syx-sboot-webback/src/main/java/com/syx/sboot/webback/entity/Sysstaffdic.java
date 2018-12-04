package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysstaffdicEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysstaffdic")
public class Sysstaffdic extends DataEntity<Sysstaffdic> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String staffid;
   @DbField
   private String dictype;
   @DbField
   private String dicvalue;
   @DbField
   private String dictext;

   public Sysstaffdic() {
       super();
   }

   public Sysstaffdic(String id) {
       super(id);
   }

   public void setStaffid(String staffid){
       this.staffid = staffid;
   }

   public String getStaffid(){
       return this.staffid;
   }
   public void setDictype(String dictype){
       this.dictype = dictype;
   }

   public String getDictype(){
       return this.dictype;
   }
   public void setDicvalue(String dicvalue){
       this.dicvalue = dicvalue;
   }

   public String getDicvalue(){
       return this.dicvalue;
   }
   public void setDictext(String dictext){
       this.dictext = dictext;
   }

   public String getDictext(){
       return this.dictext;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}