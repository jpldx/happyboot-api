package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 账号状态提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/13
 */
@Data
public class SysUserStatusForm implements Serializable {

    private List<String> ids;

    private Integer status;

}
