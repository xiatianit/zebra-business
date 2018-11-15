
package com.zebra.zebrabusiness.dal.dataobject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 电车DO
 * 
 * @author owen
 *
 */
public class BikeDO  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            bikeCode;
    private String            bikeNo;
    private String            bikeModel;
    private String            cityCode;
    private String            cityName;
    private String            zoneCode;
    private String            zoneName;
    private Long              siteId;
    private String            siteName;
    private String            bikePlateNo;
    private Integer           plateEndTime;
    private Integer           bikeSource;
    private Integer           bikeStatus;
    private String            batteryCode;
    private BigDecimal        latitude;
    private BigDecimal        longitude;
    private String            currentAddress;
    private String            limitArea;
    private Integer           operaTime;
    private Integer           warrantyStart;
    private Integer           warrantyEnd;
    private String            warrantyRemark;
    private Long              sid;
    /**
     * 所属加盟商编号
     */
    private String franchiserCode;
    /**
     * 所属加盟商名称
     */
    private String franchiserName;
    /**
     * GPRS手机号码
     */
    private String gprsPhone;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 电池电量(单位:%)
     */
    private Integer batteryElec;
    /**
     * 剩余里程数(单位:KM)
     */
    private Double restMileage;

    /** 扩展字段 */
    private Integer           leftElec;
    private Double            leftMiles;
    private Double            targetDistance;

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public Double getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(Double targetDistance) {
        this.targetDistance = targetDistance;
    }

    public String getLimitArea() {
        return limitArea;
    }

    public void setLimitArea(String limitArea) {
        this.limitArea = limitArea;
    }

    public Integer getLeftElec() {
        return leftElec;
    }

    public void setLeftElec(Integer leftElec) {
        this.leftElec = leftElec;
    }

    public Double getLeftMiles() {
        return leftMiles;
    }

    public void setLeftMiles(Double leftMiles) {
        this.leftMiles = leftMiles;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getBikeCode() {
        return bikeCode;
    }

    public void setBikeCode(String bikeCode) {
        this.bikeCode = bikeCode;
    }

    public String getBikeNo() {
        return bikeNo;
    }

    public BikeDO setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
        return this;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getBikePlateNo() {
        return bikePlateNo;
    }

    public void setBikePlateNo(String bikePlateNo) {
        this.bikePlateNo = bikePlateNo;
    }

    public Integer getPlateEndTime() {
        return plateEndTime;
    }

    public void setPlateEndTime(Integer plateEndTime) {
        this.plateEndTime = plateEndTime;
    }

    public Integer getBikeSource() {
        return bikeSource;
    }

    public void setBikeSource(Integer bikeSource) {
        this.bikeSource = bikeSource;
    }

    public Integer getBikeStatus() {
        return bikeStatus;
    }

    public void setBikeStatus(Integer bikeStatus) {
        this.bikeStatus = bikeStatus;
    }

    public String getBatteryCode() {
        return batteryCode;
    }

    public void setBatteryCode(String batteryCode) {
        this.batteryCode = batteryCode;
    }

    public Integer getOperaTime() {
        return operaTime;
    }

    public void setOperaTime(Integer operaTime) {
        this.operaTime = operaTime;
    }

    public Integer getWarrantyStart() {
        return warrantyStart;
    }

    public void setWarrantyStart(Integer warrantyStart) {
        this.warrantyStart = warrantyStart;
    }

    public Integer getWarrantyEnd() {
        return warrantyEnd;
    }

    public void setWarrantyEnd(Integer warrantyEnd) {
        this.warrantyEnd = warrantyEnd;
    }

    public String getWarrantyRemark() {
        return warrantyRemark;
    }

    public void setWarrantyRemark(String warrantyRemark) {
        this.warrantyRemark = warrantyRemark;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

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

    public String getGprsPhone() {
        return gprsPhone;
    }

    public void setGprsPhone(String gprsPhone) {
        this.gprsPhone = gprsPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBatteryElec() {
        return batteryElec;
    }

    public void setBatteryElec(Integer batteryElec) {
        this.batteryElec = batteryElec;
    }

    public Double getRestMileage() {
        return restMileage;
    }

    public void setRestMileage(Double restMileage) {
        this.restMileage = restMileage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
