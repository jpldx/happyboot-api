package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysAttrConfigDO;
import org.happykit.happyboot.sys.model.form.SysAttrConfigForm;
import org.happykit.happyboot.sys.model.query.SysAttrConfigPageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 服务接口类
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
public interface SysAttrConfigService extends IService<SysAttrConfigDO> {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysAttrConfigDO> listSysAttrConfigsByPage(SysAttrConfigPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysAttrConfigDO saveSysAttrConfig(SysAttrConfigForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysAttrConfigDO updateSysAttrConfig(SysAttrConfigForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysAttrConfig(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysAttrConfig(Long... ids);

    boolean CheckBatchIsSuccess();

    void UpdateBatchSuccess();
}

