package com.zebra.zebrabusiness.biz.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.zebra.common.constant.ZebraConstant;
import com.zebra.pony.common.model.Result;
import com.zebra.pony.common.utils.ResultUtils;
import com.zebra.pony.util.DateUtil;
import com.zebra.pony.util.JsonUtil;
import com.zebra.pony.util.RandomUtil;
import com.zebra.pony.util.ShortMessageUtil;
import com.zebra.pony.util.StringUtil;
import com.zebra.zebrabusiness.biz.enums.ResultErrorEnum;
import com.zebra.zebrabusiness.biz.exception.ZebrabusinessException;
import com.zebra.zebrabusiness.biz.pojo.DynamicParamConfig;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;
import com.zebra.zebrabusiness.biz.service.DeviceService;
import com.zebra.zebrabusiness.biz.service.LoginService;
import com.zebra.zebrabusiness.biz.service.StaffService;
import com.zebra.zebrabusiness.dal.dataobject.DeviceDO;
import com.zebra.zebrabusiness.dal.dataobject.StaffBaseDO;

/***
 * token 获取
 *
 * @author owen
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource
    private DeviceService deviceService;

    @Resource
    private StaffService staffService;

    @Autowired
    private DynamicParamConfig dynamicParamConfig;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        String s = "13388601971";
        System.out.println(s.substring(0, 3));
        System.out.println(s.substring(7, 11));
    }

    /**
     * 发送短信验证码
     *
     * @param telphone
     * @return
     */
    public void doGetLgnAuthCode(String telphone) {
        try {
            // 1、发送短信
            String randomNum = RandomUtil.getFourRandom();
            String message = "【斑马电车】验证码：" + randomNum + "，15分钟内输入有效，斑马电车，轻松出行";
            ShortMessageUtil.sendSms(message, telphone);
            logger.info("验证码短信发送：telphone={},message={}", telphone, message);

            // 2、存redis
            valueOps.set(ZebraConstant.REIDS_PREFIX_STAFF_LOGIN_AUTH + telphone, randomNum, dynamicParamConfig.getUserLoginAuthCodeEffectiveTime(),
                    TimeUnit.SECONDS);
        } catch (IOException e) {
            logger.error("验证码短信发送失败：telphone={},e={}", telphone, e);
            throw new ZebrabusinessException("验证码短信发送失败");
        }
    }

    /**
     * 登录信息
     *
     * @param deviceDO
     */
    public Result<?> doLoginIn(DeviceDO deviceDO, String telphone, String loginAuthCode) {
        int nowTime = DateUtil.getCurrentTimeSeconds();
        if ("18888888888".equals(telphone) && "1234".equals(loginAuthCode)) {
            logger.info("测试账户telphone={},loginAuthCode={}", telphone, loginAuthCode);
        } else {
            // 1、验证用户登录手机验证码是否失效
            String redisLoginAuthCode = valueOps.get(ZebraConstant.REIDS_PREFIX_STAFF_LOGIN_AUTH + telphone);
            if (StringUtil.isBlank(redisLoginAuthCode)) {
                return ResultUtils.genResult(ResultErrorEnum.LOGIN_AUTHCODE_EFFECTIVE.getErrorCode(),
                        ResultErrorEnum.LOGIN_AUTHCODE_EFFECTIVE.getErrorMsg());
            }

            // 2.1、比手机号是否匹配
            if (!telphone.equals(telphone)) {
                return ResultUtils.genResult(ResultErrorEnum.LOGIN_AUTHCODE_COMPARE_ERROR.getErrorCode(),
                        ResultErrorEnum.LOGIN_AUTHCODE_COMPARE_ERROR.getErrorMsg());
            }

            // 2.2、对比校验码是否正确
            if (!redisLoginAuthCode.equals(loginAuthCode)) {
                return ResultUtils.genResult(ResultErrorEnum.LOGIN_AUTHCODE_COMPARE_ERROR.getErrorCode(),
                        ResultErrorEnum.LOGIN_AUTHCODE_COMPARE_ERROR.getErrorMsg());
            }

            // 3、设备号
            if (StringUtils.isEmpty(deviceDO.getDeviceId())) {
                return ResultUtils.genResult(ResultErrorEnum.DEVICE_INPUT_ERROR.getErrorCode(), ResultErrorEnum.DEVICE_INPUT_ERROR.getErrorMsg());
            }
        }

        // 3、如果数据库中，用户存在，则不做处理，用户不存在，给出提示。
        StaffBaseDO staffBaseDO = staffService.queryStaffBaseByPhone(telphone);
        if (staffBaseDO == null) {
            return ResultUtils.genResult(1, "该客户端只限工作人员使用，普通用户请打开C端APP。");
        } else {
            // 这种情况，得把以前存在token都删掉。
            logger.info("用户删除redis_token,sid={}", staffBaseDO.getSid());
            List<DeviceDO> list = deviceService.queryDeviceBySid(staffBaseDO.getSid());
            if (list != null && list.size() > 0) {
                for (DeviceDO d : list) {
                    logger.info("用户删除redis_token,token={}", d.getToken().trim());
                    stringRedisTemplate.delete(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + d.getToken().trim());
                }
            }

            // 4、单点登录：同一手机用户、同一设备号，只能在同一时间登录 token、uid-phone、deviceId,receiveId
            logger.info("用户删除device_token,sid={}", staffBaseDO.getSid());
            deviceService.delDeviceBySid(staffBaseDO.getSid());
        }

        // 5、生成新的token
        String tokenStr = UUID.randomUUID().toString().replaceAll("-", "");
        deviceDO.setSid(staffBaseDO.getSid());
        deviceDO.setToken(tokenStr);
//        deviceDO.setLastUpdateTime(nowTime);
        DeviceDO device = deviceService.queryDeviceByPk(deviceDO.getDeviceId());
        if (null != device) {
            deviceService.editDevice(deviceDO);
        } else {
            deviceService.addDevice(deviceDO);
        }
        valueOps.set(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + tokenStr, buildstaffTokenStr(nowTime, deviceDO),
                dynamicParamConfig.getStaffTokenEffectiveTime(), TimeUnit.SECONDS);
        return ResultUtils.genResultWithSuccess("token", tokenStr);
    }

    /**
     * @param time
     * @param 
     * @return
     */
    private String buildstaffTokenStr(Integer time, DeviceDO deviceDO) {
        StaffToken staffToken = new StaffToken();
        staffToken.setDeviceId(deviceDO.getDeviceId());
        staffToken.setDeviceType(null != deviceDO.getDeviceType() ? deviceDO.getDeviceType() : 0);
        staffToken.setTime(time);
        staffToken.setSid(deviceDO.getSid());
        staffToken.setTokenStr(deviceDO.getToken());
        staffToken.setCurrentReceiveId(deviceDO.getCurrentReceiveId());
        return JsonUtil.getJsonFromObject(staffToken);
    }

    /**
     * 刷新token
     *
     * @param deviceDO
     */
    public Result<?> doRefreshToken(DeviceDO deviceDO) {
        int nowTime = DateUtil.getCurrentTimeSeconds();

        // 1、获取用户的token信息
        String staffTokenStr = valueOps.get(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + deviceDO.getToken());
        logger.info("刷新token,deviceDO={},staffTokenStr={}", deviceDO, staffTokenStr);

        StaffToken staffToken = JsonUtil.getObjectFromJson(staffTokenStr, StaffToken.class);
        if (StringUtil.isBlank(staffTokenStr)) {
            return ResultUtils.genResult(ResultErrorEnum.USER_TOKEN_INVALID.getErrorCode(), ResultErrorEnum.USER_TOKEN_INVALID.getErrorMsg());
        }

        // 2、deviceID先不做验证处理, TODO token 对应的设备不是同一个

        // 3、重新设置失效用户token失效时间。
        deviceDO.setSid(staffToken.getSid());
        valueOps.set(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + deviceDO.getToken(), buildstaffTokenStr(nowTime, deviceDO),
                dynamicParamConfig.getStaffTokenEffectiveTime(), TimeUnit.SECONDS);

        return ResultUtils.genResultWithSuccess();
    }

    /**
     * 四位密码确认
     */
    @Override
    public Result<?> confirmPassword(StaffToken staffToken, String passWord) {
        logger.info("员工四位密码确认，staffToken={},passWord={}", staffToken, passWord);
        StaffBaseDO staffBaseDO = staffService.queryStaffBaseByPk(staffToken.getSid());
        if (staffBaseDO != null && staffBaseDO.getPassword().equals(passWord)) {
            return ResultUtils.genResultWithSuccess();
        } else if (staffBaseDO == null) {
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "该工作人员不存在!");
        } else if (!staffBaseDO.getPassword().equals(passWord)) {
            return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "输入密码错误！");
        }
        return ResultUtils.genResult(ResultUtils.RESULT_STATUS_FAIL, "登录异常");
    }

    /**
     * 退出信息
     *
     * @param
     */

    public void doLoginOut(String tokenStr) { // 设置token对象为空
        valueOps.set(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + tokenStr, "", 3, TimeUnit.SECONDS);
    }

}
