package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 消息管理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_msg")
public class SysMsgDO extends BaseEntity {
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 发送人id
     */
    private String senderId;
    /**
     * 发送人
     */
    private String sender;
    /**
     * 内容概要
     */
    private String overview;
    /**
     * 优先级（L/低 M/中 H/高）
     */
    private String priority;
    /**
     * 消息类型（1/系统消息 2/待办消息）
     */
    private String msgCategory;
    /**
     * 发送对象类型（USER/指定用户 ALL/全体用户）
     */
    private String msgType;
    /**
     * 发送状态（0/未发送 1/已发送 2/已撤销）
     */
    private String sendStatus;
    /**
     * 发送时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    /**
     * 撤销时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;
    /**
     * 指定用户ids
     */
    private String userIds;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门code
     */
    private String deptCode;
    /**
     * 是否综治中心
     */
    private String isLeadDept;

    /**
     * 阅读状态（0/未读 1/已读）
     */
    @TableField(exist = false)
    private String readFlag;
    /**
     * uid
     */
    @TableField(exist = false)
    private String uid;
}
