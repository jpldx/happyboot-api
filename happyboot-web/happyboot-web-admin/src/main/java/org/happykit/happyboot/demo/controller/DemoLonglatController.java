package org.happykit.happyboot.demo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.demo.mapper.DemoLonglatMapper;
import org.happykit.happyboot.demo.model.entity.DemoLonglatDO;
import org.happykit.happyboot.demo.model.form.DemoLonglatForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 经纬度测试
 *
 * @author chen.xudong
 * @version 1.0 2020/7/11
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/demo/longlat")
public class DemoLonglatController extends BaseController {
    private DemoLonglatMapper demoLonglatMapper;

    public DemoLonglatController(DemoLonglatMapper demoLonglatMapper) {
        this.demoLonglatMapper = demoLonglatMapper;
    }

    @PostMapping("/add")
    public R add(@RequestBody DemoLonglatForm form) {
        DemoLonglatDO entity = new DemoLonglatDO();
        BeanUtils.copyProperties(form, entity);
        demoLonglatMapper.insert(entity);
        return success("成功");
    }

    @GetMapping("/list")
    public R list() {
        List<DemoLonglatDO> list = demoLonglatMapper.list();
        return success(list);
    }
}
