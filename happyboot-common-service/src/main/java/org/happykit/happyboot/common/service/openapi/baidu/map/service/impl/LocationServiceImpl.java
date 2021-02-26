package org.happykit.happyboot.common.service.openapi.baidu.map.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.happykit.happyboot.common.service.openapi.baidu.map.service.ILocationService;
import org.happykit.happyboot.common.service.openapi.constant.OpenApiConstant;
import org.happykit.happyboot.common.service.sysconfig.service.ISystemConfigService;
import org.happykit.happyboot.constant.CommonConstant;
import org.happykit.happyboot.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 位置服务
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@Slf4j
@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    private ISystemConfigService systemConfigService;

    @Override
    public String getAddressByIp(String ip) {
        Map<String, Object> param = new HashMap<>();
        param.put("ip", ip);
        param.put("ak", systemConfigService.getValueByKey("AK_BAIDU_MAP"));
        HttpResponse resp = HttpRequest.get("http://api.map.baidu.com/location/ip")
                .form(param)
                .timeout(20000)
                .execute();
        int httpStatus = resp.getStatus();
        if (httpStatus != HttpStatus.OK.value()) {
            log.error("百度地图API - IP定位调用失败，状态码：" + httpStatus);
            return CommonConstant.UNKNOWN;
        }
        JSONObject json = JSON.parseObject(resp.body());
        String status = json.getString("status");
        if (!OpenApiConstant.SUCCESS_CODE.equals(status)) {
            String message = json.getString("message");
            log.error("百度地图API - IP定位调用失败，失败原因：" + message);
            return CommonConstant.UNKNOWN;
        }
        return json.getJSONObject("content").getString("address");
    }
}
