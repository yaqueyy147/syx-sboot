package com.syx.sboot.webback.entity;

import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysdicEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysdic")
public class Sysdic extends DataEntity<Sysdic> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String cateid;
   @DbField
   private String catekey;
   @DbField
   private String parentid;
   @DbField
   private String checktree;
   @DbField
   private String dickey;
   @DbField
   private String dicvalue;
   @DbField
   private String minvalue;
   @DbField
   private String maxvalue;
   @DbField
   private String var06;
   @DbField
   private String var07;
   @DbField
   private String var08;
   @DbField
   private String var09;
   @DbField
   private String var10;

   public Sysdic() {
       super();
   }

   public Sysdic(String id) {
       super(id);
   }

   public void setCateid(String cateid){
       this.cateid = cateid;
   }

   public String getCateid(){
       return this.cateid;
   }
   public void setCatekey(String catekey){
       this.catekey = catekey;
   }

   public String getCatekey(){
       return this.catekey;
   }
   public void setParentid(String parentid){
       this.parentid = parentid;
   }

   public String getParentid(){
       return this.parentid;
   }
   public void setChecktree(String checktree){
       this.checktree = checktree;
   }

   public String getChecktree(){
       return this.checktree;
   }
   public void setDickey(String dickey){
       this.dickey = dickey;
   }

   public String getDickey(){
       return this.dickey;
   }
   public void setDicvalue(String dicvalue){
       this.dicvalue = dicvalue;
   }

   public String getDicvalue(){
       return this.dicvalue;
   }
   public void setMinvalue(String minvalue){
       this.minvalue = minvalue;
   }

   public String getMinvalue(){
       return this.minvalue;
   }
   public void setMaxvalue(String maxvalue){
       this.maxvalue = maxvalue;
   }

   public String getMaxvalue(){
       return this.maxvalue;
   }
   public void setVar06(String var06){
       this.var06 = var06;
   }

   public String getVar06(){
       return this.var06;
   }
   public void setVar07(String var07){
       this.var07 = var07;
   }

   public String getVar07(){
       return this.var07;
   }
   public void setVar08(String var08){
       this.var08 = var08;
   }

   public String getVar08(){
       return this.var08;
   }
   public void setVar09(String var09){
       this.var09 = var09;
   }

   public String getVar09(){
       return this.var09;
   }
   public void setVar10(String var10){
       this.var10 = var10;
   }

   public String getVar10(){
       return this.var10;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}