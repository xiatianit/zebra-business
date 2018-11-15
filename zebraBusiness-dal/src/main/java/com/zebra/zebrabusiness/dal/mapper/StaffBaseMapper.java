package com.zebra.zebrabusiness.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;



public interface StaffBaseMapper {

    /****************** 基础的增删改查 ********************/
    /**
     * 查询
     *
     * @param id
     * @return
     */
    StaffBaseDO selectByPk(@Param("sid") Long sid);
    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    StaffBaseDO selectByPhone(@Param("phone") String phone);

    /**
     * 插入
     *
     * @param staffBaseDO
     * @return
     */
    int insert(StaffBaseDO staffBaseDO);

    /**
     * 更新
     *
     * @param staffBaseDO
     * @return
     */
    int update(StaffBaseDO staffBaseDO);

    /**
     * 获取服务员工基本信息
     * 
     * @param serviceStationDO
     * @return
     */
    List<StaffBaseDO> selectByCondition(StaffBaseDO staffBaseDO);

    /**
     * 获取服务员工基本信息个数
     * 
     * @param siteDO
     * @return
     */
    int selectCountByCondition(StaffBaseDO staffBaseDO);

}
