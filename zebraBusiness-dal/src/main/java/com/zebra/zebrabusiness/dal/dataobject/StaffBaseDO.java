package com.zebra.zebrabusiness.dal.dataobject;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工基本信息DO
 * 
 * @author owen
 *
 */
public class StaffBaseDO extends PageDO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long              sid;
    private String            staffName;
    private String            idCardNo;
    private String            nickName;
    private String            phone;
    private String            password;
    private Integer           staffStatus;
    private String            headPortrait;
    private Integer           registerTime;
    private String franchiserCode;
    private String franchiserName;

    public String getFranchiserCode() {
        return franchiserCode;
    }

    public void setFranchiserCode(String franchiserCode) {
        this.franchiserCode = franchiserCode;
    }

    public String getFranchiserName() {
        return franchiserName;
    }

    public void setFranchiserName(String franchiserName) {
        this.franchiserName = franchiserName;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(Integer staffStatus) {
        this.staffStatus = staffStatus;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Integer registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
