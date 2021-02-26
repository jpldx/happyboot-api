package org.happykit.happyboot.common.service.openapi.tencent.poi.controller;//package org.happykit.happyboot.common.service.poi.controller;
//
//import com.baomidou.mybatisplus.extension.api.R;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.happykit.happyboot.modules.poi.model.PlaceData;
//import org.happykit.happyboot.modules.poi.service.IPOIService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * [通用] 位置服务
// *
// * @author chen.xudong
// * @date 2020/8/8
// */
//@Slf4j
//@RestController
//@RequestMapping("/common/poi")
//public class POIController {
//
//    @Autowired
//    private IPOIService poiService;
//
//    /**
//     * 地图
//     *
//     * @param keyword   关键词
//     * @param longlat   纬经度
//     * @param pageIndex 页码（默认1）
//     * @param pageSize  条数（默认10）
//     * @return
//     */
//    @GetMapping("/map")
//    public R map(@RequestParam(name = "keyword", required = false) String keyword,
//                 @RequestParam(name = "longlat[]") String[] longlat,
//                 @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
//                 @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
//        if (null == longlat) {
//            return R.failed("纬经度不能为空");
//        }
//        if (longlat.length != 2) {
//            return R.failed("纬经度格式错误");
//        }
//        if (StringUtils.isBlank(keyword)) {
//            return R.ok(poiService.geocoder(longlat, pageIndex, pageSize));
//        }
//        List<PlaceData> placeData = poiService.searchPlace(keyword, longlat, pageIndex, pageSize);
//        Map<String, List<PlaceData>> map = new HashMap<>(1);
//        map.put("pois", placeData);
//        return R.ok(map);
//    }
//}
