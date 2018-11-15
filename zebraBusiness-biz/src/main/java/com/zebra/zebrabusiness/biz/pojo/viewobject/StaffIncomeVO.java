
package com.zebra.zebrabusiness.biz.pojo.viewobject;

import java.util.List;

import com.zebra.zebrabusiness.dal.dataobject.StaffIncomeDO;

/** 
* @author xiatian  
* @version 创建时间：2017年4月11日 下午5:57:42 
* 类说明 
*/
public class StaffIncomeVO {

	private String year;
	private List<StaffIncomeDO> staffIncomeDOList;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<StaffIncomeDO> getStaffIncomeDOList() {
		return staffIncomeDOList;
	}
	public void setStaffIncomeDOList(List<StaffIncomeDO> staffIncomeDOList) {
		this.staffIncomeDOList = staffIncomeDOList;
	}
	
	
}
 