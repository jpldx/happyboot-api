package org.happykit.happyboot.common.service.openapi.tencent.poi.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.common.service.openapi.tencent.poi.model.GeocoderData;
import org.happykit.happyboot.common.service.openapi.tencent.poi.model.POIRespModel;
import org.happykit.happyboot.common.service.openapi.tencent.poi.model.PlaceData;
import org.happykit.happyboot.common.service.openapi.tencent.poi.service.IPOIService;
import org.happykit.happyboot.common.service.sysconfig.service.ISystemConfigService;
import org.happykit.happyboot.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen.xudong
 * @date 2020/8/8
 */
@Slf4j
@Service
public class POIServiceImpl implements IPOIService {
    @Autowired
    private ISystemConfigService systemConfigService;

    private String getPOIKey() {
        return systemConfigService.getValueByKey("KEY_TENCENT_POI");
    }

    @Override
    public List<PlaceData> searchPlace(String keyword, String[] longlat, int pageIndex, int pageSize) {
        Map<String, Object> param = new HashMap<>();
        // TODO 默认500m，不自动扩大范围，可添加配置
        param.put("boundary", "nearby(" + longlat[0] + "," + longlat[1] + ",500,0)");
        param.put("keyword", keyword);
        param.put("page_index", pageIndex);
        param.put("page_size", pageSize);
        param.put("key", this.getPOIKey());
        HttpResponse resp = HttpRequest.get("https://apis.map.qq.com/ws/place/v1/search")
                .form(param)
                .timeout(20000)
                .execute();
        int respStatus = resp.getStatus();
        if (HttpStatus.HTTP_OK != respStatus) {
            log.error("腾讯位置服务 - 地点搜索API调用失败，状态码：" + respStatus);
            throw new SysException();
        }
        POIRespModel respModel = JSON.parseObject(resp.body(), POIRespModel.class);
        if (0 != respModel.getStatus()) {
            log.error("腾讯位置服务 - 地点搜索API调用失败，失败原因：" + respModel.getMessage());
            throw new SysException();
        }
        return respModel.getData();
    }

    @Override
    public GeocoderData geocoder(String[] longlat, int pageIndex, int pageSize) {
        // TODO 默认500m，不自动扩大范围，可添加配置
        Map<String, Object> param = new HashMap<>();
        param.put("location", StringUtils.join(longlat, ","));
        param.put("get_poi", "1");
        param.put("poi_options", "radius=500;page_index=" + pageIndex + ";page_size=" + pageSize);
        param.put("key", this.getPOIKey());
        HttpResponse resp = HttpRequest.get("https://apis.map.qq.com/ws/geocoder/v1")
                .form(param)
                .timeout(20000)
                .execute();
        int respStatus = resp.getStatus();
        if (HttpStatus.HTTP_OK != respStatus) {
            log.error("腾讯位置服务 - 逆地址解析API调用失败，状态码：" + respStatus);
            throw new SysException();
        }
        POIRespModel respModel = JSON.parseObject(resp.body(), POIRespModel.class);
        if (0 != respModel.getStatus()) {
            log.error("腾讯位置服务 - 逆地址解析API调用失败，失败原因：" + respModel.getMessage());
            throw new SysException();
        }
        return respModel.getResult();
    }
}
