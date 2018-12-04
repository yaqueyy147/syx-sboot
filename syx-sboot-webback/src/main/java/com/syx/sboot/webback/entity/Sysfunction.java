package com.syx.sboot.webback.entity;


import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.DbCodeField;
import com.syx.sboot.common.entity.DbField;
import com.syx.sboot.common.entity.DbTableField;
import com.syx.sboot.webback.utils.DbConstants;

/**
* SysfunctionEntity
* @author hua.yang
* @date 2017-07-21
*/
@DbCodeField(value = DbConstants.DB_CODE)
@DbTableField(value="sysfunction")
public class Sysfunction extends DataEntity<Sysfunction> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String appid;
   @DbField
   private String parentid;
   @DbField
   private String funtionkey;
   @DbField
   private String functionname;
   @DbField
   private int orderno;
   @DbField
   private String linkurl;
   @DbField
   private String iconlink;
   @DbField
   private String buttoncheck;
   @DbField
   private String globalcheck;
   @DbField
   private String checkshow;

   public Sysfunction() {
       super();
   }

   public Sysfunction(String id) {
       super(id);
   }

   public void setAppid(String appid){
       this.appid = appid;
   }

   public String getAppid(){
       return this.appid;
   }
   public void setParentid(String parentid){
       this.parentid = parentid;
   }

   public String getParentid(){
       return this.parentid;
   }
   public void setFuntionkey(String funtionkey){
       this.funtionkey = funtionkey;
   }

   public String getFuntionkey(){
       return this.funtionkey;
   }
   public void setFunctionname(String functionname){
       this.functionname = functionname;
   }

   public String getFunctionname(){
       return this.functionname;
   }
   public void setOrderno(int orderno){
       this.orderno = orderno;
   }

   public int getOrderno(){
       return this.orderno;
   }
   public void setLinkurl(String linkurl){
       this.linkurl = linkurl;
   }

   public String getLinkurl(){
       return this.linkurl;
   }
   public void setIconlink(String iconlink){
       this.iconlink = iconlink;
   }

   public String getIconlink(){
       return this.iconlink;
   }
   public void setButtoncheck(String buttoncheck){
       this.buttoncheck = buttoncheck;
   }

   public String getButtoncheck(){
       return this.buttoncheck;
   }
   public void setGlobalcheck(String globalcheck){
       this.globalcheck = globalcheck;
   }

   public String getGlobalcheck(){
       return this.globalcheck;
   }
   public void setCheckshow(String checkshow){
       this.checkshow = checkshow;
   }

   public String getCheckshow(){
       return this.checkshow;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}