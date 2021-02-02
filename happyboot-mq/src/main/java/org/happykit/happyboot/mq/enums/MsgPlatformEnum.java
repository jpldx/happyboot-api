package org.happykit.happyboot.mq.enums;

import lombok.Getter;

/**
 * 消息平台
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/13
 */
@Getter
public enum MsgPlatformEnum {
    /**
     * 系统消息
     * need:user id
     */
    SYS_MSG,
    /**
     * APP通知消息
     * need:user id
     */
    APP_MSG,
    /**
     * 短信消息
     * need:user phone
     */
    SMS_MSG,
    /**
     * 微信消息
     * need user openid
     */
    WECHAT_MSG,
    /**
     * Socket消息
     * need user id
     */
    SOCKET_MSG
}
