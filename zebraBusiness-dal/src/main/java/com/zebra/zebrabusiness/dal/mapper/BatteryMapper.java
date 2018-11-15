package com.zebra.zebrabusiness.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.BatteryDO;

public interface BatteryMapper {

    /****************** 基础的增删改查 ********************/
    /**
     * 查询
     *
     * @param id
     * @return
     */
    BatteryDO selectByPk(@Param("batteryCode") String batteryCode);
    /**
     * 查询
     *
     * @param id
     * @return
     */
    List<BatteryDO> selectBySid(@Param("sid") Long sid);
}
