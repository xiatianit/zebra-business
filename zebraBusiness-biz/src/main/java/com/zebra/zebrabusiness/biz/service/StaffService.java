package com.zebra.zebrabusiness.biz.service;

import java.util.List;

import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;




/**
 * 员工
 * @author owen
 */
public interface StaffService {

    /**
     * 添加员工信息
     * @param staffBaseDO
     */
    void addStaffBase(StaffBaseDO staffBaseDO);
    
    /**
     * 修改员工信息
     * @param staffBaseDO
     */
    void editStaffBase(StaffBaseDO staffBaseDO);
    
    /**
     * 获取员工信息
     * @param staffBaseDO
     * @return
     */
    List<StaffBaseDO> queryStaffBaseList(StaffBaseDO staffBaseDO);
    
    /**
     * 获取员工辆数
     * @param staffBaseDO
     * @return
     */
    int queryStaffBaseCount(StaffBaseDO staffBaseDO);
    
    
    /**
     * 获取员工基本信息，根据主键 sid
     * @param id
     * @return
     */
    StaffBaseDO queryStaffBaseByPk(Long sid);
    
    /**
     * 根据手机号查询员工基本信息
     * @param telphone
     * @return
     */
    StaffBaseDO queryStaffBaseByPhone(String phone);
    
}
