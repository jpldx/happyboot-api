package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * App更新
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_update")
public class SysUpdateDO {
    /**
     * id
     */
    @TableId
    private String updateId;
    /**
     * 更新内容
     */
    private String updateContent;
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 文件地址
     */
    private String blobUrl;
    /**
     * 文件版本号
     */
    private String blobVersion;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonView({View.SecurityView.class})
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonView({View.SecurityView.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @Version
    @JsonIgnore
    private Integer version;

    @TableLogic
    @TableField("is_deleted")
    @JsonIgnore
    private Boolean deleted;

    /* 以下是数据库中没有的字段 */
    /**
     * 创建者名称
     */
    @TableField(exist = false)
    private String createUserName;
}
