package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cly
 * @version 1.0 2020/07/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attr_config")
public class SysAttrConfigDO extends BaseEntity {
    /**
     *
     */
    private String key;
    /**
     *
     */
    private String value;
}
