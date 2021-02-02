package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能点分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFacilityPageQueryParam extends PageQuery {
    /**
     * 功能点名称
     */
    private String facilityName;
    /**
     * 功能平台
     */
    private String facilityPlatform;
    /**
     * 功能类型
     */
    private String facilityType;
}
