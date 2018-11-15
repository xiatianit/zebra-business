package com.zebra.zebrabusiness.biz.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.zebrabusiness.biz.service.BatteryService;
import com.zebra.zebrabusiness.dal.dataobject.BatteryDO;
import com.zebra.zebrabusiness.dal.mapper.BatteryMapper;

/**
 * 电池服务信息
 * 
 * @author owen
 */
@Service
public class BatteryServiceImpl implements BatteryService {

	@Autowired
	private BatteryMapper masterBatteryMapper;
	@Autowired
	private BatteryMapper slaveBatteryMapper;
	@Override
	public List<BatteryDO> getStaffBatteryList(Long sid) {
		return slaveBatteryMapper.selectBySid(sid);
	}

	
	 
    /**
     * 获取电池信息，根据主键 batteryCode
     * @param batteryCode
     * @return
     */
	public BatteryDO queryBatteryByPk(String batteryCode){
	    return slaveBatteryMapper.selectByPk(batteryCode);
	}
  

}
