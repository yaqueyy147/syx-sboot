package com.syx.sboot.entity;

import com.syx.sboot.common.entity.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
@DbCodeField(value = "")
@DbTableField(value = "t_family")
public class TFamily extends DataEntity<TFamily> implements Serializable {
    private static final long serialVersionUID = 836392985203284743L;
//    @DbField(value = "id")
//    @IdField
//    private int id;
    @DbField(value = "family_first_name")
    private String familyFirstName;
    @DbField(value = "family_name")
    private String familyName;
    @DbField(value = "photo_url")
    private String photoUrl;
    @DbField(value = "create_man")
    private String createMan;
    @DbField(value = "visit_status")
    private Integer visitStatus;
    @DbField(value = "visit_password")
    private String visitPassword;
    @DbField(value = "create_time")
    private Date createTime;
    @DbField(value = "state")
    private Integer state;
    @DbField(value = "remark")
    private String remark;
    @DbField(value = "family_desc")
    private String familyDesc;
    @DbField(value = "family_area")
    private int familyArea;
    @DbField(value = "province")
    private String province;
    @DbField(value = "city")
    private String city;
    @DbField(value = "district")
    private String district;
    @DbField(value = "supplement_flag")
    private int supplementFlag;
    @DbField(value = "create_id")
    private String createId;

    public TFamily(){

    }

    public TFamily(String id){
        super(id);
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getFamilyFirstName() {
        return familyFirstName;
    }

    public void setFamilyFirstName(String familyFirstName) {
        this.familyFirstName = familyFirstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getVisitPassword() {
        return visitPassword;
    }

    public void setVisitPassword(String visitPassword) {
        this.visitPassword = visitPassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFamilyDesc() {
        return familyDesc;
    }

    public void setFamilyDesc(String familyDesc) {
        this.familyDesc = familyDesc;
    }

    public int getFamilyArea() {
        return familyArea;
    }

    public void setFamilyArea(int familyArea) {
        this.familyArea = familyArea;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getSupplementFlag() {
        return supplementFlag;
    }

    public void setSupplementFlag(int supplementFlag) {
        this.supplementFlag = supplementFlag;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFamily tFamily = (TFamily) o;

        if (id != tFamily.id) return false;
        if (familyFirstName != null ? !familyFirstName.equals(tFamily.familyFirstName) : tFamily.familyFirstName != null)
            return false;
        if (familyName != null ? !familyName.equals(tFamily.familyName) : tFamily.familyName != null) return false;
        if (photoUrl != null ? !photoUrl.equals(tFamily.photoUrl) : tFamily.photoUrl != null) return false;
        if (createMan != null ? !createMan.equals(tFamily.createMan) : tFamily.createMan != null) return false;
        if (visitStatus != null ? !visitStatus.equals(tFamily.visitStatus) : tFamily.visitStatus != null) return false;
        if (visitPassword != null ? !visitPassword.equals(tFamily.visitPassword) : tFamily.visitPassword != null)
            return false;
        if (createTime != null ? !createTime.equals(tFamily.createTime) : tFamily.createTime != null) return false;
        if (state != null ? !state.equals(tFamily.state) : tFamily.state != null) return false;
        if (remark != null ? !remark.equals(tFamily.remark) : tFamily.remark != null) return false;
        if (familyDesc != null ? !familyDesc.equals(tFamily.familyDesc) : tFamily.familyDesc != null) return false;
        if (familyArea != tFamily.familyArea) return false;
        if (province != null ? !province.equals(tFamily.province) : tFamily.province != null) return false;
        if (city != null ? !city.equals(tFamily.city) : tFamily.city != null) return false;
        if (district != null ? !district.equals(tFamily.district) : tFamily.district != null) return false;
        if (supplementFlag != tFamily.supplementFlag) return false;
        if (createId != null ? !createId.equals(tFamily.createId) : tFamily.createId != null) return false;
        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + (familyFirstName != null ? familyFirstName.hashCode() : 0);
//        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
//        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
//        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
//        result = 31 * result + (visitStatus != null ? visitStatus.hashCode() : 0);
//        result = 31 * result + (visitPassword != null ? visitPassword.hashCode() : 0);
//        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
//        result = 31 * result + (state != null ? state.hashCode() : 0);
//        result = 31 * result + (remark != null ? remark.hashCode() : 0);
//        result = 31 * result + (familyDesc != null ? familyDesc.hashCode() : 0);
//        result = 31 * result + familyArea;
//        result = 31 * result + (province != null ? province.hashCode() : 0);
//        result = 31 * result + (city != null ? city.hashCode() : 0);
//        result = 31 * result + (district != null ? district.hashCode() : 0);
//        result = 31 * result + supplementFlag;
//        result = 31 * result + (createId != null ? createId.hashCode() : 0);
//        return result;
//    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
