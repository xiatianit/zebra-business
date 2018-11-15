package com.zebra.zebrabusiness.api.helper;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.zebra.common.constant.ZebraConstant;
import com.zebra.pony.util.JsonUtil;
import com.zebra.zebrabusiness.biz.pojo.bean.StaffToken;

/**
 * token工具类
 * 
 * @author owen
 *
 */
@Repository
public class TokenHelper {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    /**
     * 根据token 获取用户对象信息（员工）
     * 
     * @param token
     * @return
     */
    public StaffToken getSidByToken(String token) {
        String staffTokenStr = valueOps.get(ZebraConstant.REDIS_PREFIX_STAFF_TOKEN + token);
        if (!StringUtils.isEmpty(staffTokenStr)) {
            return JsonUtil.getObjectFromJson(staffTokenStr, StaffToken.class);
        }
        return null;
    }
}
