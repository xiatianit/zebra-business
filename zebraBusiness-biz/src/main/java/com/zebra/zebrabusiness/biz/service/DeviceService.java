package com.zebra.zebrabusiness.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.DeviceDO;



/**
 * 设备
 * 
 * @author owen
 */
public interface DeviceService {

    /**
     * 添加设备信息
     * 
     * @param deviceDO
     */
    void addDevice(DeviceDO deviceDO);

    /**
     * 修改设备信息
     * 
     * @param deviceDO
     */
    void editDevice(DeviceDO deviceDO);

    /**
     * 删除设备(通过UID)
     * 
     * @param uid
     */
    void delDeviceByUid(@Param("uid") Long uid);

    /**
     * 删除设备(通过SID)
     * 
     * @param sid
     */
    void delDeviceBySid(@Param("sid") Long sid);
    /**
     * 获取设备信息，根据主键ID
     * 
     * @param id
     * @return
     */
    DeviceDO queryDeviceByPk(String deviceId);

    /**
     * 根据UID来获取设备信息
     * 
     * @param uid
     * @return
     */
    List<DeviceDO> queryDeviceByUid(@Param("uid") Long uid);

    /**
     * 根据SID来获取设备信息
     * 
     * @param sid
     * @return
     */
    List<DeviceDO> queryDeviceBySid(@Param("sid") Long sid);
}
