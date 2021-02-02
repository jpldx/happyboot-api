package org.happykit.happyboot.modules.poi.service;

import org.happykit.happyboot.modules.poi.model.GeocoderData;
import org.happykit.happyboot.modules.poi.model.PlaceData;

import java.util.List;

/**
 * 腾讯位置服务
 *
 * @author chen.xudong
 * @date 2020/8/8
 */
public interface IPOIService {
    /**
     * 地点搜索
     *
     * @param keyword   关键词
     * @param longlat   经纬度数组
     * @param pageIndex 页码
     * @param pageSize  条数
     * @return
     */
    List<PlaceData> searchPlace(String keyword, String[] longlat, int pageIndex, int pageSize);

    /**
     * 逆地址解析
     *
     * @param longlat   经纬度数组
     * @param pageIndex 页码
     * @param pageSize  条数
     * @return
     */
    GeocoderData geocoder(String[] longlat, int pageIndex, int pageSize);

}
