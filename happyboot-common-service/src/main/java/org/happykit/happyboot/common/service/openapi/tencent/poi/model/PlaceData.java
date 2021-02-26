package org.happykit.happyboot.common.service.openapi.tencent.poi.model;

import lombok.Data;

/**
 * 地点搜索结果
 *
 * @author chen.xudong
 * @date 2020/8/8
 */
@Data
public class PlaceData {
    /**
     * 唯一标识
     */
    private String id;
    /**
     * 名称
     */
    private String title;
    /**
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String tel;
    /**
     * 分类
     */
    private String category;
    /**
     * 类型 0:普通POI / 1:公交车站 / 2:地铁站 / 3:公交线路 / 4:行政区划
     */
    private Integer type;
    /**
     * 坐标
     */
    private Location location;
    /**
     * 行政区划信息
     */
    private AdInfo ad_info;
    /**
     * 该POI到逆地址解析传入的坐标的直线距离
     */
    private Number _distance;
}

/**
 * 坐标
 */
@Data
class Location {
    /**
     * 纬度
     */
    private Number lat;
    /**
     * 经度
     */
    private Number lng;
}

/**
 * 行政区划信息
 */
@Data
class AdInfo {
    /**
     * 行政区划代码1
     */
    private Long adcode;
    /**
     * 行政区划代码2
     */
    private String nation_code;
    /**
     * 行政区划名称
     */
    private String name;
    /**
     * 行政区划中心点坐标
     */
    private Location location;
    /**
     * 国家
     */
    private String nation;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
}