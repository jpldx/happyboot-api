package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统用户导入提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Data
public class SysUserImportForm implements Serializable {
    @NotNull(message = "文件ID必须填")
    private Long fileId;
}
