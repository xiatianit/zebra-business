package com.zebra.zebrabusiness.biz.pojo.viewobject;

import java.util.List;

/**
 * @author xiatian
 * @version 创建时间：2017年4月10日 下午1:32:16 类说明 员工管理的电池
 */
public class StaffBatteryVO {

    private List<StaffCenterVO> list;

    private Integer             batteryNumsTotal;

    public List<StaffCenterVO> getList() {
        return list;
    }

    public void setList(List<StaffCenterVO> list) {
        this.list = list;
    }

    public Integer getBatteryNumsTotal() {
        return batteryNumsTotal;
    }

    public void setBatteryNumsTotal(Integer batteryNumsTotal) {
        this.batteryNumsTotal = batteryNumsTotal;
    }

    @Override
    public String toString() {
        return "StaffBatteryVO [list=" + list + ", batteryNumsTotal=" + batteryNumsTotal + "]";
    }

}
