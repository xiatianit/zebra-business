package com.zebra.zebrabusiness.dal.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 电池DO
 * 
 * @author owen
 *
 */
public class BatteryDO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            batteryCode;
    private String            batteryModel;
    private Double            batteryCapacity;
    private Integer           batteryElec;
    private String            terminalServerAddress;
    private Double            restMileage;
    private String            bikeCode;
    private String            lastLocation;
    private String            gprsNo;
    private Double            gprsRestCharge;
    private Integer           batteryStatus;
    private BigDecimal        latitude;
    private BigDecimal        longitude;
    private Integer           operaTime;
    private Integer           warrantyStart;
    private Integer           warrantyEnd;
    private String            warrantyRemark;
    private Long              sid;
    private String            remark;

    public String getTerminalServerAddress() {
        return terminalServerAddress;
    }

    public void setTerminalServerAddress(String terminalServerAddress) {
        this.terminalServerAddress = terminalServerAddress;
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

    public String getBatteryCode() {
        return batteryCode;
    }

    public void setBatteryCode(String batteryCode) {
        this.batteryCode = batteryCode;
    }

    public String getBatteryModel() {
        return batteryModel;
    }

    public void setBatteryModel(String batteryModel) {
        this.batteryModel = batteryModel;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
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

    public String getBikeCode() {
        return bikeCode;
    }

    public void setBikeCode(String bikeCode) {
        this.bikeCode = bikeCode;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getGprsNo() {
        return gprsNo;
    }

    public void setGprsNo(String gprsNo) {
        this.gprsNo = gprsNo;
    }

    public Double getGprsRestCharge() {
        return gprsRestCharge;
    }

    public void setGprsRestCharge(Double gprsRestCharge) {
        this.gprsRestCharge = gprsRestCharge;
    }

    public Integer getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(Integer batteryStatus) {
        this.batteryStatus = batteryStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
