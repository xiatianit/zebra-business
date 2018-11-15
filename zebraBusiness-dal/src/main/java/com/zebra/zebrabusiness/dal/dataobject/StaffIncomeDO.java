package com.zebra.zebrabusiness.dal.dataobject;

import java.io.Serializable;

/**
 * 员工收入DO
 * 
 * @author xiatian
 *
 */
public class StaffIncomeDO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Long              id;
    private Long              sid;
    private Double            currentMonthIncome;
    private Double            totalIncome;
    private String            bankInfo;
    private String            year;
    private String            month;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Double getCurrentMonthIncome() {
        return currentMonthIncome;
    }

    public void setCurrentMonthIncome(Double currentMonthIncome) {
        this.currentMonthIncome = currentMonthIncome;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "StaffIncomeDO [sid=" + sid + ", currentMonthIncome=" + currentMonthIncome + ", totalIncome=" + totalIncome + ", bankInfo=" + bankInfo
                + ", year=" + year + ", month=" + month + "]";
    }

}
