package org.happykit.happyboot.op.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.happykit.happyboot.base.BaseEntity;
import org.happykit.happyboot.model.model.ImageArray;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 知识库
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("op_knowledge")
public class OpKnowledgeDO extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private String label;
    /**
     * 图片
     */
    private String image;
    /**
     * 内容
     */
    private String content;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pubTime;
    /**
     * 访问量
     */
    private Integer visits;
    /**
     * 描述
     */
    private String des;
    /**
     * 排序号
     */
    private Integer sortOrder;
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门code
     */
    private String deptCode;
    /**
     * 图片数组
     */
    @TableField(exist = false)
    private ImageArray imageArr;
}
