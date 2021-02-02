package org.happykit.happyboot.sys.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户excel对象
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Data
public class SysUserData {
    /**
     * 用户名
     */
    @ExcelProperty(index = 0)
    private String username;
    /**
     * 昵称
     */
    @ExcelProperty(index = 1)
    private String nickname;
}
