package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.form.SysMsgForm;
import org.happykit.happyboot.sys.model.query.SysMsgPageQueryParam;

/**
 * 消息管理
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/07/06
 */
public interface SysMsgService extends IService<SysMsgDO> {

    /**
     * 分页列表
     *
     * @param param
     * @return
     */
    IPage<SysMsgDO> page(SysMsgPageQueryParam param);

    /**
     * 添加
     *
     * @param form
     * @return
     */
    boolean add(SysMsgForm form);

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    boolean edit(SysMsgForm form);

    /**
     * 发送
     *
     * @param id
     * @return
     */
    boolean sendMsg(String id);

    /**
     * 撤销
     *
     * @param id
     * @return
     */
    boolean cancelMsg(String id);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteBatch(String ids);
}

