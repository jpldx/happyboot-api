package org.happykit.happyboot.sys.model.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息管理提交表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Data
public class SysMsgForm implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 内容概要
     */
    private String overview;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
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
     * 是否综治中心
     */
    private String isLeadDept;
}