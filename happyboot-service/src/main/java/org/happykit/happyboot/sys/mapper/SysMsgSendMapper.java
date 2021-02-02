package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.entity.SysMsgSendDO;
import org.happykit.happyboot.sys.model.query.SysMsgSendPageQueryParam;
import org.happykit.happyboot.sys.model.vo.MsgBatchReadVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 我的消息
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
public interface SysMsgSendMapper extends BaseMapper<SysMsgSendDO> {

    List<SysMsgDO> queryUserMsgAlertList(@Param("userId") String userId,
                                         @Param("headId") String headId,
                                         @Param("limits") int limits);

    int batchRead(@Param("param") MsgBatchReadVO param, @Param("userId") String userId);

    List<SysMsgDO> loadMore(@Param("tailId") String tailId, @Param("userId") String userId);

    IPage<SysMsgDO> page(Page<SysMsgDO> page,
                         @Param("query") SysMsgSendPageQueryParam query,
                         @Param("userId") String userId);

    int read(@Param("msgIds") List<String> msgIds, @Param("userId") String userId);
}
