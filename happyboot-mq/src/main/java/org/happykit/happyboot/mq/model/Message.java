package org.happykit.happyboot.mq.model;

import org.happykit.happyboot.mq.enums.MsgPlatformEnum;
import org.happykit.happyboot.util.IdGenerator;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 消息队列数据格式
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/13
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1619000546531112290L;

    /**
     * id（消息唯一标识）
     */
    private final String id = IdGenerator.generateIdStr();

    /**
     * 时间标识
     */
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    /**
     * 系统消息id
     */
    private String sysMsgId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息模板key
     */
    private String templateKey;

    /**
     * 消息模板占位值
     */
    private List<String> templateValues;

    /**
     * 消息接收人id
     */
    private String userId;
    /**
     * 消息平台
     */
    private List<MsgPlatformEnum> platforms;

    /**
     * 额外参数
     */
    private Map<String, Object> extras;

    /**
     * 系统消息构造器
     *
     * @param sysMsgId
     * @param userId
     * @param platforms
     * @param extras
     */
    public Message(String sysMsgId, String userId, List<MsgPlatformEnum> platforms, Map<String, Object> extras) {
        this.sysMsgId = sysMsgId;
        this.userId = userId;
        this.platforms = platforms;
        this.extras = extras;
    }

    /**
     * 自定义消息构造器
     *
     * @param title
     * @param content
     * @param userId
     * @param platforms
     * @param extras
     */
    public Message(String title, String content, String userId, List<MsgPlatformEnum> platforms, Map<String, Object> extras) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.platforms = platforms;
        this.extras = extras;
    }

    /**
     * 模板消息构造器
     *
     * @param userId
     * @param templateKey
     * @param templateVlues
     * @param platforms
     * @param extras
     */
    public Message(String userId, String templateKey, List<String> templateVlues, List<MsgPlatformEnum> platforms, Map<String, Object> extras) {
        this.templateKey = templateKey;
        this.templateValues = templateVlues;
        this.userId = userId;
        this.platforms = platforms;
        this.extras = extras;
    }
}
