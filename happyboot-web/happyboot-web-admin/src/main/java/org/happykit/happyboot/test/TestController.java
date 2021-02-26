package org.happykit.happyboot.test;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.common.service.openapi.baidu.map.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ILocationService locationService;

    @GetMapping("/ip")
    public R ip(String ip) {
        return R.ok(locationService.getAddressByIp(ip));
    }
}
