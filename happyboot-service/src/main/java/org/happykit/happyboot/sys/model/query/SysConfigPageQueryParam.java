package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigPageQueryParam extends PageQuery {
    /**
     * 参数名
     */
    private String key;
    /**
     * 类型
     */
    private String type;
}
