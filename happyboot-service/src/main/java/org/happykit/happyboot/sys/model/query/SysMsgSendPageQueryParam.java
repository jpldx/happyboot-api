package org.happykit.happyboot.sys.model.query;

import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的消息分页列表查询对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMsgSendPageQueryParam extends PageQuery {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送人
     */
    private String sender;
    /**
     * 阅读状态（0/已读 1/未读）
     */
    private String readFlag;
}
