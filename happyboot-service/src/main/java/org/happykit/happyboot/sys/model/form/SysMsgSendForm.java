package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户通告阅读标记表提交类
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Data
public class SysMsgSendForm implements Serializable {
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 阅读状态（0未读，1已读）
     */
    private String readFlag;
    /**
     * 阅读时间
     */
    private Date readTime;
}