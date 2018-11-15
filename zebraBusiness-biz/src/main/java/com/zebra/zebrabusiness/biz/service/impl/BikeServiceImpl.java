package com.zebra.zebrabusiness.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.zebra.common.constant.ZebraConstant;
import com.zebra.common.dto.BikeRelInfoDTO;
import com.zebra.common.dto.BikeRelInfoRdsDTO;
import com.zebra.pony.util.JsonUtil;
import com.zebra.zebrabusiness.biz.service.BikeService;
import com.zebra.zebrabusiness.dal.dataobject.BikeDO;
import com.zebra.zebrabusiness.dal.mapper.BikeMapper;

/**
 * 电车服务信息
 * 
 * @author owen
 */
@Service
public class BikeServiceImpl implements BikeService {

    private final static Logger             logger = LoggerFactory.getLogger(BikeServiceImpl.class);

    @Autowired
    private BikeMapper                      masterBikeMapper;

    @Autowired
    private BikeMapper                      slaveBikeMapper;

    @Resource
    private RabbitTemplate                  rabbitTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Override
    public List<BikeDO> getBikeList(Long sid) {
        return slaveBikeMapper.selectBySid(sid);
    }

    /**
     * 获取返厂中辆列表信息
     * 
     * @param sid
     * @return
     */
    @Override
    public List<BikeDO> queryNeedRepairBySid(Long sid) {
        return slaveBikeMapper.selectNeedRepairBySid(sid);
    }

    /**
     * 根据bike获取车辆信息
     * 
     * @param bikeCode
     * @return
     */
    public BikeDO queryBikeByBikeCode(String bikeCode) {
        return slaveBikeMapper.selectByPk(bikeCode);
    }

    /**
     * 更新
     *
     * @param bikeDO
     * @return
     */
    public int editBike(BikeDO bikeDO) {
        return masterBikeMapper.update(bikeDO);
    }

    /**
     * 根据用户、用户当前所在地的经纬度，获取任务车辆信息(维修、换电池)
     * 
     * @param franchiserCode
     * @param localLongitude
     * @param localLatitude
     * @return
     */
    public List<BikeDO> queryTaskBikeByCondition(String franchiserCode, BigDecimal localLongitude, BigDecimal localLatitude) {
        return slaveBikeMapper.selectTaskBikeByCondition(franchiserCode, localLongitude, localLatitude);
    }

    /***
     * 通知终端发出铃声找出车辆
     * 
     * @param bikeCode
     */
    public void doRingSearchBike(String bikeCode) {
        logger.info("mq(员工)通知终端发出铃声寻车，bikeCode={}", bikeCode);
        BikeDO bikeDO = slaveBikeMapper.selectByPk(bikeCode);
        String jsv = valueOps.get(ZebraConstant.REDIS_BIKE_REL_INFO + bikeCode + "_" + bikeDO.getBatteryCode());
        BikeRelInfoRdsDTO bikeRelInfoRdsDTO = JsonUtil.getObjectFromJson(jsv, BikeRelInfoRdsDTO.class);
        String t = bikeRelInfoRdsDTO.getTerminalAddress();
        String[] terminalAddressArr = t.split(":");
        BikeRelInfoDTO bikeRelInfoDTO = new BikeRelInfoDTO(bikeCode, bikeDO.getBatteryCode(), terminalAddressArr[0], terminalAddressArr[1], null,
                null);
        rabbitTemplate.convertAndSend(ZebraConstant.MQ_RING, JsonUtil.getJsonFromObject(bikeRelInfoDTO));
    }

}
