package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能组分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFacilityGroupPageQueryParam extends PageQuery {
    /**
     * 功能组名称
     */
    private String facilityGroupName;
}
