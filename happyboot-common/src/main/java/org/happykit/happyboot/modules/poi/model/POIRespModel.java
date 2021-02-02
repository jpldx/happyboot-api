package org.happykit.happyboot.modules.poi.model;

import lombok.Data;

import java.util.List;

/**
 * 腾讯位置服务API返回结果
 *
 * @author chen.xudong
 * @date 2020/8/8
 */
@Data
public class POIRespModel {
    /**
     * 请求唯一标识
     */
    private String request_id;
    /**
     * 状态码
     */
    private Long status;
    /**
     * 状态说明
     */
    private String message;
    /**
     * 结果数
     */
    private Long count;
    /**
     * 返回结果 - 地点搜索结果
     */
    private List<PlaceData> data;
    /**
     * 返回结果 - 逆地址解析结果
     */
    private GeocoderData result;
}
