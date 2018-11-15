
package com.zebra.zebrabusiness.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zebra.zebrabusiness.dal.dataobject.StaffIncomeDO;

/** 
* @author xiatian  
* @version 创建时间：2017年4月11日 下午4:22:03 
* 类说明 
*/
public interface StaffIncomeService {

	 /**
	  * 添加员工收入信息
	  * @param staffIncomeDO
	  */
		void addStaffIncome (StaffIncomeDO staffIncomeDO);
	    /**
	     * 查询员工当月收入
	     *
	     * @param sid
	     * @return
	     */
		StaffIncomeDO queryStaffCurrentIncome(@Param("sid") Long sid,@Param("month") String month);
	   /**
	    * 查询员工历史收入
	    * @param sid
	    */
	    List<StaffIncomeDO> queryStaffHistoryIncome(@Param("sid") Long sid);
	
}
 