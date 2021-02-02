package org.happykit.happyboot.sys.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统消息批量标记已读
 *
 * @author chen.xudong
 * @date 2020/7/10
 */
@Data
public class MsgBatchReadVO {
    /**
     * 栈首消息id
     */
    @NotBlank(message = "栈首消息id不能为空")
    private String headId;
    /**
     * 栈尾消息id
     */
    @NotBlank(message = "栈尾消息id不能为空")
    private String tailId;
}
