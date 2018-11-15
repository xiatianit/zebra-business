package com.zebra.zebrabusiness.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.DeviceDO;



public interface DeviceMapper {

    /****************** 基础的增删改查 ********************/
    /**
     * 查询
     *
     * @param deviceId
     * @return
     */
    DeviceDO selectByPk(@Param("deviceId") String deviceId);

    /**
     * 通过UID来获取设备信息
     * 
     * @param uid
     * @return
     */
    List<DeviceDO> selectByUid(@Param("uid") Long uid);
    /**
     * 通过SID来获取设备信息
     * 
     * @param sid
     * @return
     */
    List<DeviceDO> selectBySid(@Param("sid") Long sid);

    /**
     * 插入
     *
     * @param deviceDO
     * @return
     */
    int insert(DeviceDO deviceDO);

    /**
     * 删除设备(通过UID)
     * 
     * @param uid
     */
    void deleteDeviceByUid(@Param("uid") Long uid);
    
    /**
     * 删除设备(通过SID)
     * 
     * @param sid
     */
    void deleteDeviceBySid(@Param("sid") Long sid);

    /**
     * 更新
     *
     * @param deviceDO
     * @return
     */
    int update(DeviceDO deviceDO);

}