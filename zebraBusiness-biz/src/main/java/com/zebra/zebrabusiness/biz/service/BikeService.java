package com.zebra.zebrabusiness.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.zebra.zebrabusiness.dal.dataobject.BikeDO;

/**
 * 电车服务
 * 
 * @author owen
 */
public interface BikeService {
    /**
     * 根据员工sid获取员工管理的电车列表
     * 
     * @param sid
     * @return
     */
    List<BikeDO> getBikeList(Long sid);

    /**
     * 获取返厂中辆列表信息
     * 
     * @param sid
     * @return
     */
    List<BikeDO> queryNeedRepairBySid(Long sid);

    /**
     * 根据bike获取车辆信息
     * 
     * @param bikeCode
     * @return
     */
    BikeDO queryBikeByBikeCode(String bikeCode);

    /**
     * 更新
     *
     * @param bikeDO
     * @return
     */
    int editBike(BikeDO bikeDO);

    /**
     * 根据用户、用户当前所在地的经纬度，获取任务车辆信息(维修、换电池)
     * 
     * @param franchiserCode
     * @param localLongitude
     * @param localLatitude
     * @return
     */
    List<BikeDO> queryTaskBikeByCondition(String franchiserCode, BigDecimal localLongitude, BigDecimal localLatitude);
    
    /***
     * 通知终端发出铃声找出车辆
     * 
     * @param bikeCode
     */
    void doRingSearchBike(String bikeCode);

}
