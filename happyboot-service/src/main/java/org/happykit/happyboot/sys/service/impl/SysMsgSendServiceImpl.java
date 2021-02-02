package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.sys.mapper.SysMsgSendMapper;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.entity.SysMsgSendDO;
import org.happykit.happyboot.sys.model.query.SysMsgSendPageQueryParam;
import org.happykit.happyboot.sys.model.vo.MsgBatchReadVO;
import org.happykit.happyboot.sys.service.SysMsgSendService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * 我的消息
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Validated(Default.class)
@Service
public class SysMsgSendServiceImpl extends ServiceImpl<SysMsgSendMapper, SysMsgSendDO> implements SysMsgSendService {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;

    @Override
    public IPage<SysMsgDO> page(SysMsgSendPageQueryParam query) {
        return this.baseMapper.page(new Page<>(query.getPageNo(), query.getPageSize()), query, sysSecurityUtils.getCurrentUserId());
    }

    @Override
    public boolean read(String ids) {
        List<String> msgIds = Arrays.asList(ids.split(","));
        return retBool(this.baseMapper.read(msgIds, sysSecurityUtils.getCurrentUserId()));
    }

    @Override
    public boolean batchRead(MsgBatchReadVO param) {
        return retBool(this.baseMapper.batchRead(param, sysSecurityUtils.getCurrentUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysMsgSend(Long id) {
        SysMsgSendDO sysMsgSend = getById(id);
        if (sysMsgSend == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysMsgSend(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    @Override
    public List<SysMsgDO> queryUserMsgAlertList(String headId, int limits) {
        return this.baseMapper.queryUserMsgAlertList(sysSecurityUtils.getCurrentUserId(), headId, limits);
    }

    @Override
    public List<SysMsgDO> loadMore(String tailId) {
        return this.baseMapper.loadMore(tailId, sysSecurityUtils.getCurrentUserId());
    }
}