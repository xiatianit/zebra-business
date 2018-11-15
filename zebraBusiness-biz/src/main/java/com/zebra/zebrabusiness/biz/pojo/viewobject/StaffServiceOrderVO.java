package com.zebra.zebrabusiness.biz.pojo.viewobject;

import java.util.List;

/**
 * 员工服务单VO
 * 
 * @author xiatian
 *
 */
public class StaffServiceOrderVO {

    private List<StaffCenterVO> list;

    private Integer             serviceOrdersTotal;

    public List<StaffCenterVO> getList() {
        return list;
    }

    public void setList(List<StaffCenterVO> list) {
        this.list = list;
    }

    public Integer getServiceOrdersTotal() {
        return serviceOrdersTotal;
    }

    public void setServiceOrdersTotal(Integer serviceOrdersTotal) {
        this.serviceOrdersTotal = serviceOrdersTotal;
    }

}
