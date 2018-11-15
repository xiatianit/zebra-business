package com.zebra.zebrabusiness.biz.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态参数配置
 * 
 * @author owen
 */
@ConfigurationProperties(prefix = "business.param")
@Component
public class DynamicParamConfig {

    /** 员工token失效时间 24 * 60 * 60 */
    private int staffTokenEffectiveTime        = 86400;

    /** 用户发送手机验证码失效时间 15 * 60 */
    private int userLoginAuthCodeEffectiveTime = 900;

    /** 电车电池电量低于5时，则给予提示 */
    private int bikeBatteryElecQuanlityLow     = 5;

    public int getBikeBatteryElecQuanlityLow() {
        return bikeBatteryElecQuanlityLow;
    }

    public void setBikeBatteryElecQuanlityLow(int bikeBatteryElecQuanlityLow) {
        this.bikeBatteryElecQuanlityLow = bikeBatteryElecQuanlityLow;
    }

    public int getStaffTokenEffectiveTime() {
        return staffTokenEffectiveTime;
    }

    public void setStaffTokenEffectiveTime(int staffTokenEffectiveTime) {
        this.staffTokenEffectiveTime = staffTokenEffectiveTime;
    }

    public int getUserLoginAuthCodeEffectiveTime() {
        return userLoginAuthCodeEffectiveTime;
    }

    public void setUserLoginAuthCodeEffectiveTime(int userLoginAuthCodeEffectiveTime) {
        this.userLoginAuthCodeEffectiveTime = userLoginAuthCodeEffectiveTime;
    }

}
