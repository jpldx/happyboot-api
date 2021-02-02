package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysUpdateDO;
import org.happykit.happyboot.sys.model.form.SysUpdateForm;
import org.happykit.happyboot.sys.model.query.SysUpdatePageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 服务接口类
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
public interface SysUpdateService extends IService<SysUpdateDO> {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysUpdateDO> listSysUpdatesByPage(SysUpdatePageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysUpdateDO saveSysUpdate(SysUpdateForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysUpdateDO updateSysUpdate(SysUpdateForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysUpdate(@NotNull String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysUpdate(String... ids);
}

