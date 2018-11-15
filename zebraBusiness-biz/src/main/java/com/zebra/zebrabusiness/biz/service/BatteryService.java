package com.zebra.zebrabusiness.biz.service;

import java.util.List;

import com.zebra.zebrabusiness.dal.dataobject.BatteryDO;

/**
 * 电池
 * 
 * @author owen
 */
public interface BatteryService {

    List<BatteryDO> getStaffBatteryList(Long sid);
    
    /**
     * 获取电池信息，根据主键 batteryCode
     * @param batteryCode
     * @return
     */
    BatteryDO queryBatteryByPk(String batteryCode);

}
