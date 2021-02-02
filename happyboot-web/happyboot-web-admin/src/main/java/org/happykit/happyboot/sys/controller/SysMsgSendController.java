package org.happykit.happyboot.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonView;
import org.happykit.happyboot.base.BaseController;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.query.SysMsgSendPageQueryParam;
import org.happykit.happyboot.sys.model.vo.MsgBatchReadVO;
import org.happykit.happyboot.sys.service.SysMsgSendService;
import org.happykit.happyboot.util.IdGenerator;
import org.happykit.happyboot.view.View;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的消息
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/7/10
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/sys/msgSend")
public class SysMsgSendController extends BaseController {

    @Autowired
    private SysMsgSendService sysMsgSendService;

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @JsonView(value = {View.SimpleView.class})
    @GetMapping("/page")
    public R page(SysMsgSendPageQueryParam query) {
        IPage<SysMsgDO> pageList = sysMsgSendService.page(query);
        return success(pageList);
    }

    /**
     * 已读
     *
     * @param ids 消息ids
     * @return
     */
    @GetMapping("/read")
    public R read(@NotBlank @RequestParam(name = "ids") String ids) {
        return success(sysMsgSendService.read(ids));
    }

    /**
     * 批量已读
     *
     * @param param
     * @return
     */
    @PostMapping("/batchRead")
    public R batchRead(@Valid @RequestBody MsgBatchReadVO param) {
        return success(sysMsgSendService.batchRead(param));
    }

    /**
     * 消息铃铛 - 消息信息
     *
     * @param openStatus 消息查看状态（默认false）
     * @param headId     栈顶消息id（默认""）
     * @return
     */
    @JsonView(value = {View.SimpleView.class})
    @GetMapping("/queryUserMsgAlert")
    public R queryUserMsgAlert(
            @RequestParam(name = "openStatus", defaultValue = "false") boolean openStatus,
            @RequestParam(name = "headId", defaultValue = "") String headId) {
        JSONObject json = new JSONObject();
        List<SysMsgDO> msgList = sysMsgSendService.queryUserMsgAlertList(null, -1);
        json.put("amount", msgList.size());
        List<SysMsgDO> msgs = new ArrayList<>();
        if (openStatus) {
            msgs = sysMsgSendService.queryUserMsgAlertList(headId, -1);
        }
        // 设置uid配合前端组件
        for (SysMsgDO msg : msgs) {
            msg.setUid(IdGenerator.generateIdStr());
        }
        json.put("newMsgs", msgs);
        return success(json);
    }

    /**
     * 消息铃铛 - 加载更多
     *
     * @param tailId 栈尾消息id
     * @return
     */
    @JsonView(value = {View.SimpleView.class})
    @GetMapping("/loadMore")
    public R loadMore(@RequestParam(name = "tailId", defaultValue = "") String tailId) {
        List<SysMsgDO> msgs;
        if (StringUtils.isBlank(tailId)) {
            msgs = sysMsgSendService.queryUserMsgAlertList(null, 10);
        } else {
            msgs = sysMsgSendService.loadMore(tailId);
        }
        // 设置uid配合前端组件使用
        for (SysMsgDO msg : msgs) {
            msg.setUid(IdGenerator.generateIdStr());
        }
        return success(msgs);
    }
}
