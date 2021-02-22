package org.happykit.happyboot.demo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.desensitize.annotation.Sensitive;
import org.happykit.happyboot.desensitize.enums.SensitiveTypeEnum;
import org.happykit.happyboot.log.annotation.Log;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据脱敏demo
 *
 * @author shaoqiang
 * @version 1.0 2020/6/10
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/demo/sensitive")
public class SensitiveDemoController extends BaseController {

    @Log
    @GetMapping("/list")
    public R list() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setName("张三" + i);
            data.setMobile("18205832133");
            data.setIdCard("110101199003073319");
            data.setPassword("123123" + i);
            data.setRemark("备注" + i);
            list.add(data);
        }
        return success(list);
    }

    @Data
    class DemoData {
        @Sensitive(value = SensitiveTypeEnum.CHINESE_NAME)
        private String name;
        @Sensitive(value = SensitiveTypeEnum.MOBILE)
        private String mobile;
        @Sensitive(value = SensitiveTypeEnum.ID_CARD)
        private String idCard;
        @Sensitive(value = SensitiveTypeEnum.PASSWORD)
        private String password;
        private String remark;
    }
}
