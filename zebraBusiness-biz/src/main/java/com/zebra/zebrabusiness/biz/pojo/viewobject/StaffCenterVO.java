
package com.zebra.zebrabusiness.biz.pojo.viewobject; 
/** 
* @author xiatian  
* @version 创建时间：2017年4月10日 下午5:03:33 
* 类说明 
*/
public class StaffCenterVO {

	private String bikeCode;
	private String bikeNo;
	private String batteryCode;
	private Integer orderType;
	private Long  orderId;
	private Integer orderFinishTime;
	public String getBikeCode() {
		return bikeCode;
	}
	public void setBikeCode(String bikeCode) {
		this.bikeCode = bikeCode;
	}

	public String getBikeNo() {
		return bikeNo;
	}

	public void setBikeNo(String bikeNo) {
		this.bikeNo = bikeNo;
	}

	public String getBatteryCode() {
		return batteryCode;
	}
	public void setBatteryCode(String batteryCode) {
		this.batteryCode = batteryCode;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderFinishTime() {
		return orderFinishTime;
	}
	public void setOrderFinishTime(Integer orderFinishTime) {
		this.orderFinishTime = orderFinishTime;
	}
	
}
 