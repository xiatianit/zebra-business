
package com.zebra.zebrabusiness.dal.dataobject;

import java.io.Serializable;

/**
 * @author xiatian
 * @version 创建时间：2017年4月10日 下午4:01:13 类说明
 */
public class StaffServiceOrderDO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long serviceOrderId;
	private Integer orderAction;
	private Integer orderStatus;
	private Integer serviceStartTime;
	private Integer serviceEndTime;
	private Integer settleTime;
	private Integer lastModifyTime;
	private String orderContext;
	private String bikeCode;
	private String bikeNo;
	private Long sid;
	public Long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public Integer getOrderAction() {
		return orderAction;
	}
	public void setOrderAction(Integer orderAction) {
		this.orderAction = orderAction;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(Integer serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public Integer getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(Integer serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public Integer getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Integer settleTime) {
		this.settleTime = settleTime;
	}
	public Integer getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Integer lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getOrderContext() {
		return orderContext;
	}
	public void setOrderContext(String orderContext) {
		this.orderContext = orderContext;
	}
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

	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	@Override
	public String toString() {
		return "StaffServiceOrderDO [serviceOrderId=" + serviceOrderId + ", orderAction=" + orderAction
				+ ", orderStatus=" + orderStatus + ", serviceStartTime=" + serviceStartTime + ", serviceEndTime="
				+ serviceEndTime + ", settleTime=" + settleTime + ", lastModifyTime=" + lastModifyTime
				+ ", orderContext=" + orderContext + ", bikeCode=" + bikeCode + ", bikeNo=" + bikeNo +", sid=" + sid + "]";
	}
}
