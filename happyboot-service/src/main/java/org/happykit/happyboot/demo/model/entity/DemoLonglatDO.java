package org.happykit.happyboot.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
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
//@EqualsAndHashCode(callSuper = true)
@TableName(value = "demo_longlat")
public class DemoLonglatDO extends BaseEntity {
    /**
     * 数据
     */
//    @TableField(typeHandler = StringListTypeHandler.class)
    private ImageArray data;
    /**
     * 经纬度
     */
//    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Number> longLat;
}
