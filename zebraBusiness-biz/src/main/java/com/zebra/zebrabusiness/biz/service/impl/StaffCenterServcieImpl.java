package com.zebra.zebrabusiness.biz.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.pony.common.model.Result;
import com.zebra.pony.common.utils.ResultUtils;
import com.zebra.pony.util.JsonUtil;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;
import com.zebra.zebrabusiness.biz.pojo.viewobject.StaffBatteryVO;
import com.zebra.zebrabusiness.biz.pojo.viewobject.StaffBikeVO;
import com.zebra.zebrabusiness.biz.pojo.viewobject.StaffCenterVO;
import com.zebra.zebrabusiness.biz.pojo.viewobject.StaffIncomeVO;
import com.zebra.zebrabusiness.biz.pojo.viewobject.StaffServiceOrderVO;
import com.zebra.zebrabusiness.biz.service.BatteryService;
import com.zebra.zebrabusiness.biz.service.BikeService;
import com.zebra.zebrabusiness.biz.service.StaffCenterService;
import com.zebra.zebrabusiness.biz.service.StaffIncomeService;
import com.zebra.zebrabusiness.biz.service.StaffService;
import com.zebra.zebrabusiness.biz.service.StaffServiceOrderService;
import com.zebra.zebrabusiness.dal.dataobject.BatteryDO;
import com.zebra.zebrabusiness.dal.dataobject.BikeDO;
import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;
import com.zebra.zebrabusiness.dal.dataobject.StaffIncomeDO;
import com.zebra.zebrabusiness.dal.dataobject.StaffServiceOrderDO;

/**
 * @author xiatian
 * @version 创建时间：2017年3月31日 下午6:08:26 类说明
 */
@Service
public class StaffCenterServcieImpl implements StaffCenterService {

    private static final Logger      logger = LoggerFactory.getLogger(StaffCenterServcieImpl.class);
    @Resource
    private StaffService             staffService;

    @Resource
    private BikeService              bikeService;

    @Resource
    private BatteryService           batteryService;

    @Resource
    private StaffServiceOrderService staffServiceOrderService;

    @Autowired
    private StaffIncomeService       staffIncomeService;

    @Override
    public Result<?> getStaffInfo(StaffToken staffToken) {
        logger.info("获取员工个人信息,staffToken={}", JsonUtil.getJsonFromObject(staffToken));
        StaffBaseDO staffBaseDO = staffService.queryStaffBaseByPk(staffToken.getSid());
        if (staffBaseDO != null) {
            return ResultUtils.genResultWithSuccess(staffBaseDO);
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "获取不到该用户的信息");
    }

    @Override
    public Result<?> getStaffManagerBikeList(StaffToken staffToken) {
        logger.info("查询员工管理的电车,staffToken={}", staffToken);
        StaffBikeVO staffBikeVO = null;
        List<StaffCenterVO> staffCenterVOList = null;
        List<BikeDO> bikeList = bikeService.getBikeList(staffToken.getSid());
        if (bikeList != null) {
            staffBikeVO = new StaffBikeVO();
            staffCenterVOList = new ArrayList<StaffCenterVO>();
            for (BikeDO list : bikeList) {
                StaffCenterVO staffCenterVO = new StaffCenterVO();
                staffCenterVO.setBikeCode(list.getBikeCode());
                staffCenterVO.setBikeNo(list.getBikeNo());
                staffCenterVOList.add(staffCenterVO);
            }
            staffBikeVO.setList(staffCenterVOList);
            staffBikeVO.setBikeTotal(bikeList.size());
            return ResultUtils.genResultWithSuccess(staffBikeVO);
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "查询不到员工管理的电车");
    }

    @Override
    public Result<?> getStaffManagerBatteryList(StaffToken staffToken) {
        logger.info("查询员工管理的电池,staffToken={}", staffToken);
        StaffBatteryVO staffBatteryVO = null;
        List<StaffCenterVO> staffCenterVOList = null;
        List<BatteryDO> batteryList = batteryService.getStaffBatteryList(staffToken.getSid());
        if (batteryList != null) {
            staffBatteryVO = new StaffBatteryVO();
            staffCenterVOList = new ArrayList<StaffCenterVO>();
            for (BatteryDO list : batteryList) {
                StaffCenterVO staffCenterVO = new StaffCenterVO();
                staffCenterVO.setBatteryCode(list.getBatteryCode());
                staffCenterVOList.add(staffCenterVO);
            }
            staffBatteryVO.setList(staffCenterVOList);
            staffBatteryVO.setBatteryNumsTotal(batteryList.size());
            return ResultUtils.genResultWithSuccess(staffBatteryVO);
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "查询不到员工管理的电池");
    }

    @Override
    public Result<?> getMyServiceList(StaffToken staffToken, int type) {
        logger.info("查询员工的服务单,staffToken={},type={}", staffToken, type);
        StaffServiceOrderVO staffServiceOrderVO = null;
        List<StaffCenterVO> staffCenterVOList = null;
        // 0查询全部服务单，1查询换电池服务单
        if (type == 0) {
            List<StaffServiceOrderDO> staffServiceOrderList = staffServiceOrderService.getMyServiceList(staffToken.getSid(), null);
            if (staffServiceOrderList != null && staffServiceOrderList.size() > 0) {
                staffServiceOrderVO = new StaffServiceOrderVO();
                staffCenterVOList = new ArrayList<StaffCenterVO>();
                for (StaffServiceOrderDO serviceOrderDO : staffServiceOrderList) {
                    StaffCenterVO staffCenterVO = new StaffCenterVO();
                    staffCenterVO.setOrderType(serviceOrderDO.getOrderAction());
                    staffCenterVO.setBikeCode(serviceOrderDO.getBikeCode());
                    staffCenterVO.setBikeNo(serviceOrderDO.getBikeNo());
                    staffCenterVO.setOrderId(serviceOrderDO.getServiceOrderId());
                    staffCenterVO.setOrderFinishTime(serviceOrderDO.getServiceEndTime());
                    staffCenterVOList.add(staffCenterVO);
                }
                staffServiceOrderVO.setList(staffCenterVOList);
                staffServiceOrderVO.setServiceOrdersTotal(staffCenterVOList.size());
                return ResultUtils.genResultWithSuccess(staffServiceOrderVO);
            }
        } else {
            List<StaffServiceOrderDO> staffServiceOrderList = staffServiceOrderService.getMyServiceList(staffToken.getSid(), type);
            if (staffServiceOrderList != null && staffServiceOrderList.size() > 0) {
                staffServiceOrderVO = new StaffServiceOrderVO();
                staffCenterVOList = new ArrayList<StaffCenterVO>();
                for (StaffServiceOrderDO serviceOrderDO : staffServiceOrderList) {
                    StaffCenterVO staffCenterVO = new StaffCenterVO();
                    staffCenterVO.setOrderType(serviceOrderDO.getOrderAction());
                    staffCenterVO.setBikeCode(serviceOrderDO.getBikeCode());
                    staffCenterVO.setBikeNo(serviceOrderDO.getBikeNo());
                    staffCenterVO.setOrderId(serviceOrderDO.getServiceOrderId());
                    staffCenterVO.setOrderFinishTime(serviceOrderDO.getServiceEndTime());
                    staffCenterVOList.add(staffCenterVO);
                }
                staffServiceOrderVO.setList(staffCenterVOList);
                staffServiceOrderVO.setServiceOrdersTotal(staffCenterVOList.size());
                return ResultUtils.genResultWithSuccess(staffServiceOrderVO);
            }
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "查询不到该员工的服务单！");
    }

    @Override
    public Result<?> myCurrentIncome(StaffToken staffToken) {
        logger.info("查询员工当前月的收入,staffToken={}", staffToken);
        Calendar now = Calendar.getInstance();
        String month = now.get(Calendar.MONTH) + 1 + "";
        StaffIncomeDO staffIncomeDO = staffIncomeService.queryStaffCurrentIncome(staffToken.getSid(), month);
        if (staffIncomeDO != null) {
            return ResultUtils.genResultWithSuccess(staffIncomeDO);
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "查询不到员工的当前收入");
    }

    @Override
    public Result<?> myHistoryIncome(StaffToken staffToken) {
        logger.info("查询员工历史收入,staffToken={}", staffToken);
        List<StaffIncomeDO> staffIncomeList = staffIncomeService.queryStaffHistoryIncome(staffToken.getSid());
        List<StaffIncomeVO> staffVOList = null;
        StaffIncomeVO staffVO = null;
        List<StaffIncomeDO> staffDOList = null;
        Map<String, List<StaffIncomeDO>> staffMap = null;
        if (staffIncomeList != null) {
            staffMap = new HashMap<String, List<StaffIncomeDO>>();
            // 遍历查询到的员工历史收入list
            for (StaffIncomeDO list : staffIncomeList) {
                // 通过map按年份进行分拣存储
                if (!staffMap.containsKey(list.getYear())) {
                    staffDOList = new ArrayList<StaffIncomeDO>();
                    staffDOList.add(list);
                } else {
                    staffDOList.add(list);
                }
                staffMap.put(list.getYear(), staffDOList);
            }
            // 对map重新遍历分拣，包装成一个list给前台返回
            if (staffMap.size() > 0) {
                staffVOList = new ArrayList<StaffIncomeVO>();
                for (String key : staffMap.keySet()) {
                    List<StaffIncomeDO> value = staffMap.get(key);
                    staffVO = new StaffIncomeVO();
                    staffVO.setYear(key);
                    staffVO.setStaffIncomeDOList(value);
                    staffVOList.add(staffVO);
                }
            }

            return ResultUtils.genResultWithSuccess("list", staffVOList);
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "查询不到员工的历史收入");
    }

}
