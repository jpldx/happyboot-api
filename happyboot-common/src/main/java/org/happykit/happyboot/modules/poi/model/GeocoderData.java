package org.happykit.happyboot.modules.poi.model;

import lombok.Data;

import java.util.List;

/**
 * 逆地址解析结果
 *
 * @author chen.xudong
 * @date 2020/8/8
 */
@Data
public class GeocoderData {
    // 前端不需要的属性
//    /**
//     * 地址描述
//     */
//    private String address;
//    /**
//     * 周边POI总数
//     */
//    private Long poi_count;
//    /**
//     * 行政区划信息
//     */
//    private AdInfo ad_info;
    /**
     * 周边POI数组
     */
    private List<PlaceData> pois;
}
