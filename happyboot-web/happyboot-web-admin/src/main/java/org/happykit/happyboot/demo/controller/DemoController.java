package org.happykit.happyboot.demo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/10
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;
    @Autowired
    private DataSource dataSource;

    @GetMapping("/userinfo")
    public R userinfo() {
        System.out.println("hello");
        System.out.println(1 / 0);
        return success("hello world");
    }

    @GetMapping("/dataSource")
    public R dataSource() throws SQLException {
        Connection conn = dataSource.getConnection();
        return success("ok");
    }
}
