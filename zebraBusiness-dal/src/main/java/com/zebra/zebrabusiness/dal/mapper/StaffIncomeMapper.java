package com.zebra.zebrabusiness.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.StaffIncomeDO;



public interface StaffIncomeMapper {

    /****************** 基础的增删改查 ********************/
	
	void insert (StaffIncomeDO staffIncomeDO);
    /**
     * 查询员工当月收入
     *
     * @param sid
     * @return
     */
	StaffIncomeDO selectCurrentIncome(@Param("sid") Long sid,@Param("month") String month);
   /**
    * 查询员工历史收入
    * @param sid
    */
    List<StaffIncomeDO> selectHistoryIncome(@Param("sid") Long sid);
}
