
package com.zebra.zebrabusiness.biz.service;

import com.zebra.pony.common.model.Result;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;

/** 
* @author xiatian  
* @version 创建时间：2017年3月31日 下午6:06:10 
* 类说明 
*/
public interface StaffCenterService {

	public Result<?> getStaffInfo(StaffToken staffToken);
	
	public Result<?> getStaffManagerBikeList(StaffToken staffToken);

	public Result<?> getStaffManagerBatteryList(StaffToken staffToken);

	public Result<?> getMyServiceList(StaffToken staffToken,int type);

	public Result<?> myCurrentIncome(StaffToken staffToken);
	
	public Result<?> myHistoryIncome(StaffToken staffToken);
	
}
 