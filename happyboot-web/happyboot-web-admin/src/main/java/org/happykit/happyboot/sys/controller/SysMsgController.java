package org.happykit.happyboot.sys.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.annotation.CheckPermission;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.mq.constants.MqConstant;
import org.happykit.happyboot.mq.enums.MsgPlatformEnum;
import org.happykit.happyboot.mq.model.Message;
import org.happykit.happyboot.mq.service.MqService;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.form.SysMsgForm;
import org.happykit.happyboot.sys.model.query.SysMsgPageQueryParam;
import org.happykit.happyboot.sys.service.SysMsgService;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 消息管理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/7/6
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/msg")
public class SysMsgController extends BaseController {

    @Autowired
    private SysMsgService sysMsgService;

    @Autowired
    MqService mqService;


    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @JsonView(value = {View.SimpleView.class})
    @GetMapping(value = "/page")
    public R page(SysMsgPageQueryParam query) {
        // TODO 数据权限
        return success(sysMsgService.page(query));
    }

    /**
     * 添加
     *
     * @param form
     * @return
     */
    @CheckPermission
    @PostMapping(value = "/add")
    public R add(@Valid @RequestBody SysMsgForm form) {
        return success(sysMsgService.add(form));
    }

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    @CheckPermission
    @PutMapping(value = "/update")
    public R update(@Valid @RequestBody SysMsgForm form) {
        return success(sysMsgService.edit(form));
    }

    /**
     * 发送
     *
     * @param id
     * @return
     */
    @CheckPermission
    @GetMapping(value = "/sendMsg")
    public R sendMsg(@NotBlank @RequestParam(name = "id") String id) {
        return success(sysMsgService.sendMsg(id));
    }

    /**
     * 撤销
     *
     * @param id
     * @return
     */
    @CheckPermission
    @GetMapping(value = "/cancelMsg")
    public R cancelMsg(@NotBlank @RequestParam(name = "id") String id) {
        return success(sysMsgService.cancelMsg(id));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @CheckPermission
    @DeleteMapping(value = "/delete")
    public R delete(@NotBlank @RequestParam(name = "id") String id) {
        return success(sysMsgService.delete(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @CheckPermission
    @DeleteMapping(value = "/deleteBatch")
    public R deleteBatch(@NotBlank @RequestParam(name = "ids") String ids) {
        return success(sysMsgService.deleteBatch(ids));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @JsonView({View.SimpleView.class})
    public R get(@NotBlank @RequestParam(name = "id") String id) {
        SysMsgDO dbRecord = sysMsgService.getById(id);
        if (dbRecord == null) {
            return failed(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        return success(dbRecord);
    }

    @GetMapping("/sendDirectMsg")
    public String sendDirectMsg() {
        Message message = new Message("1348463515932823554",
                "1",
                Arrays.asList(MsgPlatformEnum.SYS_MSG, MsgPlatformEnum.APP_MSG),
                new HashMap<String, Object>() {{
                    put("name", "jpldx");
                    put("age", 18);
                }});
        // 发送消息
        mqService.sendDirectMessage(MqConstant.MSG_EXCHANGE, MqConstant.MSG_ROUTING_KEY, message);
        return "ok";
    }
}
