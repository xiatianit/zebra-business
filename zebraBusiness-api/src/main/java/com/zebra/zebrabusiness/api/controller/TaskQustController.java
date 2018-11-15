package com.zebra.zebrabusiness.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zebra.zebrabusiness.biz.service.*;
import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zebra.common.constant.ZebraConstant;
import com.zebra.common.dto.BikeRelInfoRdsDTO;
import com.zebra.pony.common.model.Result;
import com.zebra.pony.common.utils.ResultUtils;
import com.zebra.pony.util.DateUtil;
import com.zebra.pony.util.JsonUtil;
import com.zebra.pony.util.StringUtil;
import com.zebra.zebrabusiness.api.helper.TokenHelper;
import com.zebra.zebrabusiness.biz.enums.ResultErrorEnum;
import com.zebra.zebrabusiness.biz.pojo.DynamicParamConfig;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;
import com.zebra.zebrabusiness.dal.dataobject.BatteryDO;
import com.zebra.zebrabusiness.dal.dataobject.BikeDO;
import com.zebra.zebrabusiness.dal.dataobject.StaffServiceOrderDO;

/**
 * 任务获取
 *
 * @author owen
 */
@RestController
@RequestMapping("/quest")
public class TaskQustController {

    private final static Logger logger = LoggerFactory.getLogger(TaskQustController.class);


    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private StaffServiceOrderService staffServiceOrderService;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource
    private DynamicParamConfig dynamicParamConfig;

    @Autowired
    private StaffService staffService;

    /**
     * 任务获取
     *
     * @return
     */
    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> fetchTask(@RequestParam(value = "token", required = true) String token,
                               @RequestParam(value = "localLongitude", required = true) BigDecimal localLongitude,
                               @RequestParam(value = "localLatitude", required = true) BigDecimal localLatitude) {
        logger.info("任务获取信息，token={},localLongitude={},localLatitude={}", token, localLongitude, localLatitude);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            StaffBaseDO staffBaseDO = staffService.queryStaffBaseByPk(staffToken.getSid());
            logger.info("查询到的员工信息staffBaseDO={}", JsonUtil.getJsonFromObject(staffBaseDO));
            List<BikeDO> list = bikeService.queryTaskBikeByCondition(staffBaseDO.getFranchiserCode(), localLongitude, localLatitude);

            if (list != null && list.size() > 0) {
                Map<String, Object> firstmap = new HashMap<String, Object>();

                Map<String, Object> secondmap = new HashMap<String, Object>();
                List<Map<String, Object>> thirdlist = new ArrayList<Map<String, Object>>();
                for (BikeDO bikeDO : list) {
                    Map<String, Object> resMap = new HashMap<String, Object>();
                    // "questType": 2,//0换电池,1维修,2正常
                    resMap.put("questType", "2");
                    if (bikeDO.getBikeStatus().intValue() == ZebraConstant.BIKE_STATUS_5) {
                        resMap.put("questType", "0");
                    } else if (bikeDO.getBikeStatus().intValue() == ZebraConstant.BIKE_STATUS_8) {
                        resMap.put("questType", "1");
                    }
                    resMap.put("bikeCode", bikeDO.getBikeCode());
                    resMap.put("bikeNo", bikeDO.getBikeNo());
                    resMap.put("latitude", bikeDO.getLatitude());
                    resMap.put("longitude", bikeDO.getLongitude());
                    resMap.put("batteryCode", bikeDO.getBatteryCode());
                    resMap.put("bikeLocation", bikeDO.getCurrentAddress());
                    //BatteryDO batteryDO = batteryService.queryBatteryByPk(bikeDO.getBatteryCode());
                    //电池电量从单车中获取
                    if (null != bikeDO) {
                        resMap.put("battery", bikeDO.getBatteryElec());
                    }
                    thirdlist.add(resMap);
                }
                secondmap.put("bikeStatusList", thirdlist);

                // 返厂车辆信息
                secondmap.put("repairBikeNums", 0);
                List<BikeDO> listNeedCompany = bikeService.queryNeedRepairBySid(staffToken.getSid());
                if (listNeedCompany != null && listNeedCompany.size() > 0) {
                    secondmap.put("repairBikeNums", listNeedCompany.size());
                }

                firstmap.put("questInfo", secondmap);
                return ResultUtils.genResultWithSuccess(firstmap);
            } else {
                // 没有车子数据
                return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "没有数据任务获取");
            }
        } catch (Exception e) {
            logger.error("任务获取信息，token={},localLongitude={},localLatitude={},e={}", token, localLongitude, localLatitude, e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 2、更新车辆电池状态（[需更换电池状态] --> [可行驶]） 通过电车找到电池，然后判断电量，能否更换成功
     *
     * @param token
     * @param bikeCode
     * @return
     */
    @RequestMapping(value = "/updatebattery", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> updateBattery(@RequestParam("token") String token, @RequestParam("bikeCode") String bikeCode) {
        logger.info("更新车辆电池状态，token={},bikeCode={}", token, bikeCode);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            // 判断该车存不存在
            BikeDO bikeDO = bikeService.queryBikeByBikeCode(bikeCode);
            if (bikeDO == null) {
                logger.info("不存在该车，bikeCode={}", bikeCode);
                return ResultUtils.genResultWithSuccess("status", "1");
            }

           /* // 判断该车对应电池存不存在
            if (StringUtil.isBlank(bikeDO.getBatteryCode())) {
                logger.info("电池不存在，bikeCode={}", bikeCode);
                return ResultUtils.genResultWithSuccess("status", "1");
            }

            // 判断该车的电量是否过低 电池不存在(与该电池对应关系)，
            String jsv = valueOps.get(ZebraConstant.REDIS_BIKE_REL_INFO + bikeCode + "_" + bikeDO.getBatteryCode());
            if (StringUtil.isBlank(jsv)) {
                logger.info("电池不存在(与该电池对应关系)，bikeCode={}", bikeCode);
                return ResultUtils.genResultWithSuccess("status", "1");
            }*/

            // 判断该车的电量是否过低
            // BikeRelInfoRdsDTO bikeRelInfoRdsDTO = JsonUtil.getObjectFromJson(jsv, BikeRelInfoRdsDTO.class);

            /*if (bikeDO.getBatteryElec() <= dynamicParamConfig.getBikeBatteryElecQuanlityLow()) {
                logger.info("电池电量过低，bikeCode={},batteryElec={}", bikeCode, bikeDO.getBatteryElec());
                return ResultUtils.genResultWithSuccess("status", "1");
            }*/

            // [需更换电池状态] --> [可行驶]
            BikeDO tempBikeDO = new BikeDO();
            tempBikeDO.setBikeCode(bikeCode);
            tempBikeDO.setBikeStatus(ZebraConstant.BIKE_STATUS_3);
            int effrows = bikeService.editBike(tempBikeDO);
            if (effrows == 1) {
                // 插入服务单 订单类型(1、更换电池 2、复位车辆 3、返厂维修)
                // 订单状态(1、服务进行中 2、服务已完成 3、已完成未结算 4、已结算 5、已处理(后台处理) 6、无效)
                logger.info("插入服务单信息[更换电池-->行驶中]，bikeNo={}", bikeDO.getBikeNo());
                StaffServiceOrderDO staffServiceOrderDO = new StaffServiceOrderDO();
                staffServiceOrderDO.setSid(staffToken.getSid());
                staffServiceOrderDO.setBikeCode(bikeCode);
                staffServiceOrderDO.setBikeNo(bikeDO.getBikeNo());
                staffServiceOrderDO.setOrderAction(1);
                staffServiceOrderDO.setOrderStatus(2);
                staffServiceOrderDO.setServiceStartTime(DateUtil.getCurrentTimeSeconds());
                staffServiceOrderDO.setLastModifyTime(DateUtil.getCurrentTimeSeconds());
                staffServiceOrderDO.setOrderContext("单车" + bikeDO.getBikeNo() + "更换电池");
                staffServiceOrderService.addStaffServiceOrder(staffServiceOrderDO);
                return ResultUtils.genResultWithSuccess("status", "0");
            } else {
                return ResultUtils.genResultWithSuccess("status", "1");
            }
        } catch (Exception e) {
            logger.error("更新车辆电池状态失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 3、更新车辆维修信息 （[需维护] --> [维护返场中]）
     *
     * @param token
     * @param bikeCode
     * @return
     */
    @RequestMapping(value = "/updaterepair", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> updateRepairBike(@RequestParam("token") String token, @RequestParam("bikeCode") String bikeCode) {
        logger.info("更新车辆维修信息，token={},bikeCode={}", token, bikeCode);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }
            // 判断该车存不存在
            BikeDO bikeDO = bikeService.queryBikeByBikeCode(bikeCode);
            if (bikeDO == null) {
                logger.info("不存在该车，bikeCode={}", bikeCode);
                return ResultUtils.genResultWithSuccess("status", "1");
            }
            BikeDO bike = new BikeDO();
            bike.setBikeCode(bikeCode);
            bike.setBikeStatus(ZebraConstant.BIKE_STATUS_9);
            int effrows = bikeService.editBike(bike);
            if (effrows == 1) {
                // 插入服务单 订单类型(1、更换电池 2、复位车辆 3、返厂维修)
                // 订单状态(1、服务进行中 2、服务已完成 3、已完成未结算 4、已结算 5、已处理(后台处理) 6、无效)
                logger.info("插入服务单信息[需维护-->维修返厂中]，bikeNo={}", bikeDO.getBikeNo());
                StaffServiceOrderDO staffServiceOrderDO = new StaffServiceOrderDO();
                staffServiceOrderDO.setSid(staffToken.getSid());
                staffServiceOrderDO.setBikeCode(bikeCode);
                staffServiceOrderDO.setBikeNo(bikeDO.getBikeNo());
                staffServiceOrderDO.setOrderAction(3);
                // 等erp介入时，才改为服务已完成
                staffServiceOrderDO.setOrderStatus(1);
                staffServiceOrderDO.setServiceStartTime(DateUtil.getCurrentTimeSeconds());
                staffServiceOrderDO.setLastModifyTime(DateUtil.getCurrentTimeSeconds());
                staffServiceOrderDO.setOrderContext("单车" + bikeDO.getBikeNo() + "维修");
                staffServiceOrderService.addStaffServiceOrder(staffServiceOrderDO);
                return ResultUtils.genResultWithSuccess("status", "0");
            } else {
                return ResultUtils.genResultWithSuccess("status", "1");
            }
        } catch (Exception e) {
            logger.error("更新车辆维修信息失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 4、获取返厂中辆列表信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/needreapirlist", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> needReapirBikelList(@RequestParam("token") String token) {
        logger.info("获取返厂中辆列表信息，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            List<BikeDO> list = bikeService.queryNeedRepairBySid(staffToken.getSid());
            if (list == null) {
                return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "没有返厂中辆列表信息");
            }

            List<Map<String, String>> l = new ArrayList<Map<String, String>>();
            for (BikeDO bikeDO : list) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("bikeCode", bikeDO.getBikeCode());
                m.put("bikeNo",bikeDO.getBikeNo());
                l.add(m);
            }

            return ResultUtils.genResultWithSuccess("bikeList", l);
        } catch (Exception e) {
            logger.error("获取返厂中辆列表信息失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 5、扫码后根据车辆编号获取车辆状态
     *
     * @param token
     * @param bikeCode
     * @return
     */
    @RequestMapping(value = "/fetchBike", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> fetchBike(@RequestParam("token") String token, @RequestParam("bikeCode") String bikeCode) {
        logger.info("获取单车信息，token={}", token);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            BikeDO bikeDO = bikeService.queryBikeByBikeCode(bikeCode);
            if (bikeDO == null) {
                return ResultUtils.genResult(ResultUtils.RESULT_STATUS_SUCCESS, "没有该单车信息");
            }

            Map<String, Object> resMap = new HashMap<String, Object>();
            // "questType": 0,//0换电池,1维修,2正常
            resMap.put("questType", "2");
            if (bikeDO.getBikeStatus().intValue() == ZebraConstant.BIKE_STATUS_5) {
                resMap.put("questType", "0");
            } else if (bikeDO.getBikeStatus().intValue() == ZebraConstant.BIKE_STATUS_8) {
                resMap.put("questType", "1");
            }

            resMap.put("bikeCode", bikeCode);
            resMap.put("bikeNo", bikeDO.getBikeNo());
            resMap.put("latitude", bikeDO.getLatitude());
            resMap.put("longitude", bikeDO.getLongitude());
            resMap.put("batteryCode", bikeDO.getBatteryCode());
            resMap.put("bikeLocation", bikeDO.getCurrentAddress());
            //电池电量从单车中获取
            if (null != bikeDO.getBatteryElec()) {
                resMap.put("battery", bikeDO.getBatteryElec());
            }
           /* BatteryDO batteryDO = batteryService.queryBatteryByPk(bikeDO.getBatteryCode());
            if (batteryDO != null) {
                resMap.put("battery", batteryDO.getBatteryElec());
            }*/
            return ResultUtils.genResultWithSuccess(resMap);
        } catch (Exception e) {
            logger.error("获取单车信息失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 6、维修车辆（[需更换电池下] --> [需维修]）更换电池时，如果发现车子由损坏，则把车状态更新为，需维修
     *
     * @param token
     * @param bikeCode
     * @return
     */
    @RequestMapping(value = "/updatebatterytorepair", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> updateBatteryToRepair(@RequestParam("token") String token, @RequestParam("bikeCode") String bikeCode) {
        logger.info("更换电池时，如果发现车子由损坏，则把车状态更新为，需维修，token={},bikeCode={}", token, bikeCode);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            // 判断该车存不存在
            BikeDO bikeDO = bikeService.queryBikeByBikeCode(bikeCode);
            if (bikeDO == null) {
                logger.info("不存在该车，bikeCode={}", bikeCode);
                return ResultUtils.genResultWithSuccess("status", "1");
            }

            // [需更换电池状态] --> [需维修]
            BikeDO tempBikeDO = new BikeDO();
            tempBikeDO.setBikeCode(bikeCode);
            tempBikeDO.setBikeStatus(ZebraConstant.BIKE_STATUS_8);
            int effrows = bikeService.editBike(tempBikeDO);
            if (effrows == 1) {
                return ResultUtils.genResultWithSuccess("status", "0");
            } else {
                return ResultUtils.genResultWithSuccess("status", "1");
            }
        } catch (Exception e) {
            logger.error("更换电池时，如果发现车子由损坏，则把车状态更新为，需维修失败,e={}", e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 经纬度用以判断车辆是否在可以被"吁一下"的范围内 对车辆发出吁一下的命令
     *
     * @param token
     * @param longitude
     * @param latitude
     * @param bikeCode
     * @return
     */
    @RequestMapping(value = "/ringCallBike", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> doRingCallBike(@RequestParam(value = "token", required = true) String token,
                                    @RequestParam(value = "longitude", required = true) BigDecimal longitude,
                                    @RequestParam(value = "latitude", required = true) BigDecimal latitude, @RequestParam(value = "bikeCode", required = true) String bikeCode) {
        logger.info("员工吁一下车辆：token={},longitude={},latitude={},bikeCode={}", token, longitude, latitude, bikeCode);
        try {
            StaffToken staffToken = tokenHelper.getSidByToken(token);
            if (staffToken == null) {
                return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), "token为空");
            }

            bikeService.doRingSearchBike(bikeCode);
            return ResultUtils.genResultWithSuccess(null);
        } catch (Exception e) {
            logger.error("员工吁一下车辆信息失败：token={},longitude={},latitude={},bikeCode={},e={}", token, longitude, latitude, bikeCode, e);
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, e.getMessage());
        }
    }

}
