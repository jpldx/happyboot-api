package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询对象
 *
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFilePageQueryParam extends PageQuery {
    /**
     * 文件名称
     */
    private String fileName;
//    /**
//     * 文件类型
//     */
//    private String mimeType;
    /**
     * 上传人
     */
    private String createUserName;
}
