package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysConfigDO;
import org.happykit.happyboot.sys.model.form.SysConfigForm;
import org.happykit.happyboot.sys.model.query.SysConfigPageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 系统配置
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
public interface SysConfigService extends IService<SysConfigDO> {

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<SysConfigDO> page(SysConfigPageQueryParam query);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysConfigDO get(String id);

    /**
     * 新增
     *
     * @param form
     * @return
     */
    boolean add(SysConfigForm form);

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    boolean update(SysConfigForm form);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysConfigDO saveSysConfig(SysConfigForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysConfigDO updateSysConfig(SysConfigForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysConfig(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysConfig(Long... ids);

    /**
     * 通过参数名查询参数值
     *
     * @param key
     * @return
     */
    String getValueByKey(String key);

    /**
     * 通过参数名查询
     *
     * @param key
     * @return
     */
    SysConfigDO getByKey(String key);

}
