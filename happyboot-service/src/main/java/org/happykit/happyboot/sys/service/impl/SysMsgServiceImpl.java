package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.CommonConstant;
import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;
import org.happykit.happyboot.page.PageUtils;
import org.happykit.happyboot.sys.mapper.SysMsgMapper;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.form.SysMsgForm;
import org.happykit.happyboot.sys.model.query.SysMsgPageQueryParam;
import org.happykit.happyboot.sys.service.SysMsgSendService;
import org.happykit.happyboot.sys.service.SysMsgService;
import org.happykit.happyboot.sys.service.SysUserService;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.Date;

/**
 * 消息管理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
@Validated(Default.class)
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsgDO> implements SysMsgService {

    @Autowired
    private SysSecurityUtils sysSecurityUtils;
    @Autowired
    private SysMsgSendService sysMsgSendService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public IPage<SysMsgDO> page(SysMsgPageQueryParam param) {
        return this.baseMapper.page(new PageUtils<SysMsgPageQueryParam>().page(param, SysMsgDO.class), param);
    }

    @Override
    public boolean add(SysMsgForm form) {
        SysDeptObjDO dept = sysSecurityUtils.getCurrentUserDept();
        if (null == dept) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_DEPT);
        }
        SysMsgDO entity = new SysMsgDO();
        BeanUtils.copyProperties(form, entity);
        entity.setSubjectId(dept.getSubjectId());
        entity.setDeptId(dept.getId());
        entity.setDeptCode(dept.getDeptCode());
        // 置为未发送状态
        entity.setSendStatus(CommonConstant.MSG_STATUS_UNSENT);
        return this.save(entity);
    }

    @Override
    public boolean edit(SysMsgForm form) {
        SysMsgDO dbRecord = this.getById(form.getId());
        if (null == dbRecord) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        BeanUtils.copyProperties(form, dbRecord);
        return this.updateById(dbRecord);
    }

    @Transactional
    @Override
    public boolean sendMsg(String id) {
        SysMsgDO dbRecord = this.getById(id);
        if (dbRecord == null) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        // 置为已发送状态
        dbRecord.setSendStatus(CommonConstant.MSG_STATUS_SENT);
        dbRecord.setSendTime(new Date());
        String loginUserId = sysSecurityUtils.getCurrentUserId();
        dbRecord.setSenderId(loginUserId);
        dbRecord.setSender(sysUserService.getNicknameById(loginUserId));
        return this.updateById(dbRecord);

        // 保存消息表（目前只针对对应部门的人员发消息）
//        Set<String> userIds = sysRefUserDeptObjService.listUserIdsByDeptId(dbRecord.getDeptId());
//        for (String userId : userIds) {
//            SysMsgSendDO msgSend = new SysMsgSendDO();
//            msgSend.setMsgId(dbRecord.getId());
//            msgSend.setUserId(userId);
//            msgSend.setReadFlag(CommonConstant.MSG_STATUS_NOT_READ);
//            sysMsgSendService.save(msgSend);
//        }
        // todo APP消息推送
//		List<SysNotiUserModel> users = sysUserService.queryNotiUserListByOrgId( sysAnnouncement.getDeptId());
//		if(users.size() > 0){
//			Map<String,String> extras = new HashMap<>();
//			// APP跳转到消息页面
//			extras.put("redirect_url",sysConfigService.queryValueByKey(ConfigConstant.KEY_APP_PATH_MSG));
//			List<String> userIds =  users.stream().map(SysNotiUserModel::getUserId).collect(Collectors.toList());
//			String[] alias = userIds.toArray(new String[]{});
//			String title = sysConfigService.queryValueByKey(ConfigConstant.KEY_APP_TITLE_NOTI_SYSTEM_MSG);
//			String content = sysAnnouncement.getTitile();
//			// 消息通知
//			this.notiService.send(title, content, extras ,alias);
//		}
//		if (ok) {
//			result.success("发布成功");
//		}
    }

    @Override
    public boolean cancelMsg(String id) {
        SysMsgDO dbRecord = this.getById(id);
        if (null == dbRecord) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        dbRecord.setSendStatus(CommonConstant.MSG_STATUS_CANCEL);
        dbRecord.setCancelTime(new Date());
        return this.updateById(dbRecord);
    }

    @Override
    public boolean delete(String id) {
        SysMsgDO dbRecord = this.getById(id);
        if (null == dbRecord) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
        return this.removeById(id);
    }

    @Override
    public boolean deleteBatch(String ids) {
        return this.removeByIds(Arrays.asList(ids.split(",")));
    }
}