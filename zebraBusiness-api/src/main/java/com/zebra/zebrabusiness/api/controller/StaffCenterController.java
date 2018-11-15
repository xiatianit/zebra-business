package com.zebra.zebrabusiness.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zebra.pony.common.model.Result;
import com.zebra.pony.common.utils.ResultUtils;
import com.zebra.zebrabusiness.api.helper.TokenHelper;
import com.zebra.zebrabusiness.biz.enums.ResultErrorEnum;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;
import com.zebra.zebrabusiness.biz.service.StaffCenterService;

/**
 * 服务人员个人中心控制层
 * 
 * @author xiatian
 *
 */

@RestController
@RequestMapping("/staff")
public class StaffCenterController {

    private final static Logger logger = LoggerFactory.getLogger(StaffCenterController.class);

    @Autowired
    private StaffCenterService  StaffCenterService;

    @Autowired
    private TokenHelper         tokenHelper;

    /**
     * 获取员工信息
     * 
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> staffInfo(@RequestParam("token") String token) {
        logger.info("获取用户个人信息，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.getStaffInfo(staffToken);
        } catch (Exception e) {
            logger.error("退出登录失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }

    }

    /**
     * 获取员工管理的电车
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/managerbikelist", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> staffManagerBikeList(@RequestParam("token") String token) {
        logger.info("员工管理的电车，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.getStaffManagerBikeList(staffToken);
        } catch (Exception e) {
            logger.error("获取员工管理的电车失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 获取员工管理的电池
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/managerbatterylist", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> staffManagerBatteryList(@RequestParam("token") String token) {
        logger.info("员工管理的电池，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.getStaffManagerBatteryList(staffToken);
        } catch (Exception e) {
            logger.error("获取员工管理的电池失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 员工服务单列表
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/usermyservicelist", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> myServiceList(@RequestParam("token") String token, int type) {

        logger.info("员工服务单列表，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.getMyServiceList(staffToken, type);
        } catch (Exception e) {
            logger.error("获取员工服务单列表失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 员工当月收入
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/mycurrentincome", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> myCurrentIncome(@RequestParam("token") String token) {

        logger.info("我的收入，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.myCurrentIncome(staffToken);
        } catch (Exception e) {
            logger.error("获取员工收入失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 员工历史收入
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/myhistoryincome", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> myHistoryIncome(@RequestParam("token") String token) {

        logger.info("员工历史收入，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            return StaffCenterService.myHistoryIncome(staffToken);
        } catch (Exception e) {
            logger.error("获取员工历史收入收入失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

}
