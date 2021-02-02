package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息管理分页查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMsgPageQueryParam extends PageQuery {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 发送人
     */
    private String sender;
}
