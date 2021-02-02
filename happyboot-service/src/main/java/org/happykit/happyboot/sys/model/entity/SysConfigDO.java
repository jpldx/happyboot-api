package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置
 *
 * @author shaoqiang
 * @version 1.0 2020/06/02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfigDO extends BaseEntity {
    /**
     * 参数名
     */
    private String key;
    /**
     * 参数值
     */
    private String value;
    /**
     * 类型
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
}
