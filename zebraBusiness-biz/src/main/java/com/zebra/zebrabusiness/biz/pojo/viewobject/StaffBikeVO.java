package com.zebra.zebrabusiness.biz.pojo.viewobject;

import java.util.List;

/**
 * 员工管理电车VO
 * 
 * @author xiatian
 *
 */
public class StaffBikeVO {

    List<StaffCenterVO> list;

    private Integer     bikeTotal;

    public List<StaffCenterVO> getList() {
        return list;
    }

    public void setList(List<StaffCenterVO> list) {
        this.list = list;
    }

    public Integer getBikeTotal() {
        return bikeTotal;
    }

    public void setBikeTotal(Integer bikeTotal) {
        this.bikeTotal = bikeTotal;
    }

    @Override
    public String toString() {
        return "StaffBikeVO [list=" + list + ", bikeTotal=" + bikeTotal + "]";
    }

}
