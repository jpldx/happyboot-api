package org.happykit.happyboot.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_data")
public class DemoDataDO extends BaseEntity {
    /**
     *
     */
    private String data;
    /**
     *
     */
    private LocalDateTime date;
}
