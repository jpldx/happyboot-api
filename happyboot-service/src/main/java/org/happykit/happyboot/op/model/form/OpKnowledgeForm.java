package org.happykit.happyboot.op.model.form;

import org.happykit.happyboot.model.model.ImageArray;
import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 知识库表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
public class OpKnowledgeForm implements Serializable {

    /**
     * 知识库ID
     */
    @NotBlank(groups = Update.class)
    private String id;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 标签
     */
    private String label;
    /**
     * 图片
     */
    private ImageArray imageArr = new ImageArray();
    /**
     * 内容
     */
    private String content;
    /**
     * 描述
     */
    private String des;
    /**
     * 排序号
     */
    private Integer sortOrder;
}