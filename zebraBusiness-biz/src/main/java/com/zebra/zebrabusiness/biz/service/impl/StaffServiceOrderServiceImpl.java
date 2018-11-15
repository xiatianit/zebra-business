package com.zebra.zebrabusiness.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.zebrabusiness.biz.service.StaffServiceOrderService;
import com.zebra.zebrabusiness.dal.dataobject.StaffServiceOrderDO;
import com.zebra.zebrabusiness.dal.mapper.StaffServiceOrderMapper;

/**
 * @author xiatian
 * @version 创建时间：2017年4月10日 下午4:22:49 类说明
 */
@Service
public class StaffServiceOrderServiceImpl implements StaffServiceOrderService {

    @Autowired
    private StaffServiceOrderMapper masterStaffServiceOrderMapper;
    @Autowired
    private StaffServiceOrderMapper slaveStaffServiceOrderMapper;

    @Override
    public List<StaffServiceOrderDO> getMyServiceList(Long sid, Integer type) {
        return slaveStaffServiceOrderMapper.selectServiceBySid(sid, type);
    }

    /**
     * 添加服务单信息
     * 
     * @param
     */
    public void addStaffServiceOrder(StaffServiceOrderDO staffServiceOrderDO) {
        masterStaffServiceOrderMapper.insert(staffServiceOrderDO);
    }

}
