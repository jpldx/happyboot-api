package org.happykit.happyboot.sys.model.vo;

import lombok.Data;

/**
 * 功能VO
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
public class SysFacilityVO {
    /**
     * 功能ID
     */
    private String id;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 关联标识
     */
    private boolean flag;
    /**
     * 功能类型
     */
    private String type;
    /**
     * 功能平台
     */
    private String platform;
    /**
     * 功能描述
     */
    private String des;
}
