package com.zebra.zebrabusiness.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.pony.util.JsonUtil;
import com.zebra.zebrabusiness.biz.exception.ZebrabusinessException;
import com.zebra.zebrabusiness.biz.service.StaffService;
import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;
import com.zebra.zebrabusiness.dal.mapper.StaffBaseMapper;
import com.zebra.pony.util.DateUtil;

/**
 * 员工服务信息
 * 
 * @author owen
 */
@Service
public class StaffServiceImpl implements StaffService {

    private final static Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    @Autowired
    private StaffBaseMapper     masterStaffBaseMapper;

    @Autowired
    private StaffBaseMapper     slaveStaffBaseMapper;

    /**
     * 新增员工基础信息
     */
    @Override
    public void addStaffBase(StaffBaseDO staffBaseDO) {
        logger.info("新增员工基础信息，staffBaseDO={}", JsonUtil.getJsonFromObject(staffBaseDO));
        try {
            staffBaseDO.setRegisterTime(DateUtil.getCurrentTimeSeconds());
            int effectRows = masterStaffBaseMapper.insert(staffBaseDO);
            if (effectRows == 0) {
                throw new ZebrabusinessException("新增员工基础失败");
            }
        } catch (Exception e) {
            logger.error("新增员工基础信息失败，staffBaseDO={},e={}", JsonUtil.getJsonFromObject(staffBaseDO), e);
            throw new ZebrabusinessException("新增员工基础信息失败");
        }
    }

    /**
     * 修改员工基础信息
     */
    @Override
    public void editStaffBase(StaffBaseDO staffBaseDO) {
        logger.info("修改员工基础信息，staffBaseDO={}", JsonUtil.getJsonFromObject(staffBaseDO));
        try {
            int effectRows = masterStaffBaseMapper.update(staffBaseDO);
            if (effectRows == 0) {
                throw new ZebrabusinessException("修改员工基础失败");
            }
        } catch (Exception e) {
            logger.error("修改员工基础信息失败，staffBaseDO={},e={}", JsonUtil.getJsonFromObject(staffBaseDO), e);
            throw new ZebrabusinessException("修改员工基础信息失败");
        }
    }

    /**
     * 获取员工基本信息集合
     */
    @Override
    public List<StaffBaseDO> queryStaffBaseList(StaffBaseDO staffBaseDO) {
        return slaveStaffBaseMapper.selectByCondition(staffBaseDO);
    }

    /**
     * 获取员工基本信息数量
     */
    @Override
    public int queryStaffBaseCount(StaffBaseDO staffBaseDO) {
        return slaveStaffBaseMapper.selectCountByCondition(staffBaseDO);
    }

    /**
     * 获取员工基本信息，根据主键 sid
     * 
     * @param id
     * @return
     */
    public StaffBaseDO queryStaffBaseByPk(Long sid) {
        return slaveStaffBaseMapper.selectByPk(sid);
    }

    @Override
    public StaffBaseDO queryStaffBaseByPhone(String phone) {

        return slaveStaffBaseMapper.selectByPhone(phone);
    }

}
