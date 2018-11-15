package com.zebra.zebrabusiness.dal.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.BikeDO;

public interface BikeMapper {

    /***************** 具体的业务逻辑 *****************/

    /** 获取附近的车辆信息 **/
    BikeDO selectByPk(@Param("bikeCode") String bikeCode);

    List<BikeDO> selectBySid(@Param("sid") Long sid);

    List<BikeDO> selectNeedRepairBySid(@Param("sid") Long sid);

    /**
     * 更新
     *
     * @param bikeDO
     * @return
     */
    int update(BikeDO bikeDO);

    /**
     * 根据用户、用户当前所在地的经纬度，获取任务车辆信息(维修、换电池)
     * 
     * @param franchiserCode
     * @param localLongitude
     * @param localLatitude
     * @return
     */
    List<BikeDO> selectTaskBikeByCondition(@Param("franchiserCode") String franchiserCode, @Param("localLongitude") BigDecimal localLongitude,
            @Param("localLatitude") BigDecimal localLatitude);

}
