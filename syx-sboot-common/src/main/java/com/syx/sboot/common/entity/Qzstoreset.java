package com.syx.sboot.common.entity;

/**
* QzstoresetEntity
* @author hua.yang
* @date 2018-07-30
*/
@DbCodeField(value = "")
@DbTableField(value="qzstoreset")
public class Qzstoreset extends DataEntity<Qzstoreset> {
   private static final long serialVersionUID = 1L;

   @DbField
   private String servername;
   @DbField
   private String servertype;
   @DbField
   private String viewpath;
   @DbField
   private String useflag;
   @DbField
   private String ftpaddress;
   @DbField
   private String ftpport;
   @DbField
   private String ftpusername;
   @DbField
   private String ftppassword;
   @DbField
   private String localaddress;
   @DbField
   private String usetype;

   public Qzstoreset() {
       super();
   }

   public Qzstoreset(String id) {
       super(id);
   }

   public void setServername(String servername){
       this.servername = servername;
   }

   public String getServername(){
       return this.servername;
   }
   public void setServertype(String servertype){
       this.servertype = servertype;
   }

   public String getServertype(){
       return this.servertype;
   }
   public void setViewpath(String viewpath){
       this.viewpath = viewpath;
   }

   public String getViewpath(){
       return this.viewpath;
   }
   public void setUseflag(String useflag){
       this.useflag = useflag;
   }

   public String getUseflag(){
       return this.useflag;
   }
   public void setFtpaddress(String ftpaddress){
       this.ftpaddress = ftpaddress;
   }

   public String getFtpaddress(){
       return this.ftpaddress;
   }
   public void setFtpport(String ftpport){
       this.ftpport = ftpport;
   }

   public String getFtpport(){
       return this.ftpport;
   }
   public void setFtpusername(String ftpusername){
       this.ftpusername = ftpusername;
   }

   public String getFtpusername(){
       return this.ftpusername;
   }
   public void setFtppassword(String ftppassword){
       this.ftppassword = ftppassword;
   }

   public String getFtppassword(){
       return this.ftppassword;
   }
   public void setLocaladdress(String localaddress){
       this.localaddress = localaddress;
   }

   public String getLocaladdress(){
       return this.localaddress;
   }
   public void setUsetype(String usetype){
       this.usetype = usetype;
   }

   public String getUsetype(){
       return this.usetype;
   }

   public Object objectKeyValue() {
       return this.getId();
   }
}