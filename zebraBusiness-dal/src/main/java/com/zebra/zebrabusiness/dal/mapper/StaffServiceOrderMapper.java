package com.zebra.zebrabusiness.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.StaffServiceOrderDO;

public interface StaffServiceOrderMapper {

    /****************** 基础的增删改查 ********************/
    /**
     * 查询
     *
     * @param id
     * @return
     */
    List<StaffServiceOrderDO> selectServiceBySid(@Param("sid") Long sid, @Param("type") Integer type);

    void insert(StaffServiceOrderDO staffServiceOrderDO);
}
