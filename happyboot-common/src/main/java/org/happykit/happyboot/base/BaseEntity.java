package org.happykit.happyboot.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.view.View;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * TODO 公共基类
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CREATE_BY = "createBy";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_BY = "updateBy";
    public static final String UPDATE_TIME = "updateTime";
    /**
     * 主键id
     */
    @TableId
    private String id;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonView(View.SecurityView.class)
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonView(View.SecurityView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonView(View.SecurityView.class)
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonView(View.SecurityView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 乐观锁
     */
    @Version
    @JsonIgnore
    private Integer version;
    /**
     * 是否删除(逻辑删除)
     */
    @TableLogic
    @TableField(value = "is_deleted")
    @JsonIgnore
    private Boolean deleted;

}
