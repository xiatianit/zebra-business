package com.zebra.zebrabusiness.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.pony.util.JsonUtil;
import com.zebra.zebrabusiness.biz.exception.ZebrabusinessException;
import com.zebra.zebrabusiness.biz.service.DeviceService;
import com.zebra.zebrabusiness.dal.dataobject.DeviceDO;
import com.zebra.zebrabusiness.dal.mapper.DeviceMapper;

/**
 * 电池服务信息
 * 
 * @author owen
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private final static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceMapper        masterDeviceMapper;

    @Autowired
    private DeviceMapper        slaveDeviceMapper;

    /**
     * 添加设备
     */
    @Override
    public void addDevice(DeviceDO deviceDO) {
        logger.info("新增设备信息，deviceDO={}", JsonUtil.getJsonFromObject(deviceDO));
        try {
            int effectRows = masterDeviceMapper.insert(deviceDO);
            if (effectRows == 0) {
                throw new ZebrabusinessException("新增设备失败");
            }
        } catch (Exception e) {
            logger.error("新增设备信息失败，deviceDO={},e={}", JsonUtil.getJsonFromObject(deviceDO), e);
            throw new ZebrabusinessException("新增设备信息失败");
        }
    }

    /**
     * 修改设备
     */
    @Override
    public void editDevice(DeviceDO deviceDO) {
        logger.info("修改设备信息，deviceDO={}", JsonUtil.getJsonFromObject(deviceDO));
        try {
            int effectRows = masterDeviceMapper.update(deviceDO);
            if (effectRows == 0) {
                throw new ZebrabusinessException("修改设备失败");
            }
        } catch (Exception e) {
            logger.error("修改设备信息失败，deviceDO={},e={}", JsonUtil.getJsonFromObject(deviceDO), e);
            throw new ZebrabusinessException("修改设备信息失败");
        }
    }

    /**
     * 删除设备(通过UID)
     * 
     * @param uid
     */
    @Override
    public void delDeviceByUid(Long uid) {
        logger.info("删除设备信息，uid={}", JsonUtil.getJsonFromObject(uid));
        try {
            masterDeviceMapper.deleteDeviceByUid(uid);
        } catch (Exception e) {
            logger.error("删除设备信息，uid={},e={}", JsonUtil.getJsonFromObject(uid), e);
            throw new ZebrabusinessException("删除设备信息失败");
        }
    }

    /**
     * 查询设备
     */
    @Override
    public DeviceDO queryDeviceByPk(String deviceId) {
        return slaveDeviceMapper.selectByPk(deviceId);
    }

    /**
     * 根据UID来获取设备信息
     * 
     * @param uid
     * @return
     */
    @Override
    public List<DeviceDO> queryDeviceByUid(Long uid) {
        return slaveDeviceMapper.selectByUid(uid);
    }

	@Override
	public void delDeviceBySid(Long sid) {
		 logger.info("删除设备信息，uid={}", JsonUtil.getJsonFromObject(sid));
	        try {
	            masterDeviceMapper.deleteDeviceBySid(sid);
	        } catch (Exception e) {
	            logger.error("删除设备信息，sid={},e={}", JsonUtil.getJsonFromObject(sid), e);
	            throw new ZebrabusinessException("删除设备信息失败");
	        }
		
	}

	@Override
	public List<DeviceDO> queryDeviceBySid(Long sid) {
		return slaveDeviceMapper.selectBySid(sid);
	}

}
