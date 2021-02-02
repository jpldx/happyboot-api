package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户通告阅读标记表
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_msg_send")
public class SysMsgSendDO extends BaseEntity {
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
