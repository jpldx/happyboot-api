package org.happykit.happyboot.controller;

import org.happykit.happyboot.service.SysGeneratorService;
import org.happykit.happyboot.util.PageUtils;
import org.happykit.happyboot.util.Query;
import org.happykit.happyboot.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/24
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtil = sysGeneratorService.queryList(new Query(params));

        return R.ok().put("page", pageUtil);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));

//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
//		response.addHeader("Content-Length", "" + data.length);
//		response.setContentType("application/octet-stream; charset=UTF-8");
//
//		IOUtils.write(data, response.getOutputStream());
    }
}
