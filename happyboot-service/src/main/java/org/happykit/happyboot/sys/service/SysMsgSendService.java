package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.entity.SysMsgSendDO;
import org.happykit.happyboot.sys.model.query.SysMsgSendPageQueryParam;
import org.happykit.happyboot.sys.model.vo.MsgBatchReadVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 我的消息
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
public interface SysMsgSendService extends IService<SysMsgSendDO> {
    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<SysMsgDO> page(SysMsgSendPageQueryParam query);

    /**
     * 已读
     *
     * @param ids
     * @return
     */
    boolean read(String ids);

    /**
     * 批量已读
     *
     * @param param
     * @return
     */
    boolean batchRead(MsgBatchReadVO param);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysMsgSend(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysMsgSend(Long... ids);

    /**
     * 查询消息铃铛列表
     *
     * @param headId
     * @param limits
     * @return
     */
    List<SysMsgDO> queryUserMsgAlertList(String headId, int limits);

    /**
     * 消息铃铛列表加载更多
     *
     * @param tailId
     * @return
     */
    List<SysMsgDO> loadMore(String tailId);
}

