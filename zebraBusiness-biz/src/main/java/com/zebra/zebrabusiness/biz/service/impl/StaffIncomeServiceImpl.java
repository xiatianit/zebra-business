
package com.zebra.zebrabusiness.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.zebrabusiness.biz.service.StaffIncomeService;
import com.zebra.zebrabusiness.dal.dataobject.StaffIncomeDO;
import com.zebra.zebrabusiness.dal.mapper.StaffIncomeMapper;

/** 
* @author xiatian  
* @version 创建时间：2017年4月11日 下午4:25:28 
* 类说明 
*/
@Service
public class StaffIncomeServiceImpl implements StaffIncomeService {

	@Autowired
	private StaffIncomeMapper masterStaffIncomeMapper;
	
	@Autowired
	private StaffIncomeMapper slaveStaffIncomeMapper;
	@Override
	public void addStaffIncome(StaffIncomeDO staffIncomeDO) {
		masterStaffIncomeMapper.insert(staffIncomeDO);
	}

	@Override
	public StaffIncomeDO queryStaffCurrentIncome(Long sid, String month) {
		return slaveStaffIncomeMapper.selectCurrentIncome(sid, month);
	}

	@Override
	public List<StaffIncomeDO> queryStaffHistoryIncome(Long sid) {
		return slaveStaffIncomeMapper.selectHistoryIncome(sid);
	}

}
 