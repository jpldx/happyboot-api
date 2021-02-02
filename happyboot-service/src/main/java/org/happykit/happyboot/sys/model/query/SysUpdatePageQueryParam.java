package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询对象
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUpdatePageQueryParam extends PageQuery {
    /**
     * 文件版本号
     */
    private String blobVersion;
}
