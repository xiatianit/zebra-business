package com.zebra.zebrabusiness.biz.service;

import java.util.List;

import com.zebra.zebrabusiness.dal.dataobject.StaffServiceOrderDO;

/**
 * @author xiatian
 * @version 创建时间：2017年4月10日 下午3:47:54 类说明
 */
public interface StaffServiceOrderService {

    List<StaffServiceOrderDO> getMyServiceList(Long sid, Integer type);

    /**
     * 添加服务单信息
     * 
     * @param staffBaseDO
     */
    void addStaffServiceOrder(StaffServiceOrderDO staffServiceOrderDO);

}
