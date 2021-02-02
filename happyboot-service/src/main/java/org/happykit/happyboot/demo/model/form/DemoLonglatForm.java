package org.happykit.happyboot.demo.model.form;

import org.happykit.happyboot.model.model.ImageArray;
import lombok.Data;

import java.util.List;

/**
 * 经纬度字段测试
 *
 * @author chen.xudong
 * @version 1.0 2020/7/11
 */
@Data
public class DemoLonglatForm {
    /**
     * 数据
     */
    private ImageArray data;
    /**
     * 经纬度
     */
    private List<Number> longLat;
}
