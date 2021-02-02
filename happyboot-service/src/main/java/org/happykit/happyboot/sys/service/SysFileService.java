package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysFileDO;
import org.happykit.happyboot.sys.model.form.SysFileForm;
import org.happykit.happyboot.sys.model.query.SysFilePageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
public interface SysFileService extends IService<SysFileDO> {
    /**
     * 通过sha1获取对象
     *
     * @param sha1
     * @return
     */
    SysFileDO getBySha1(@NotNull String sha1);

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<SysFileDO> page(SysFilePageQueryParam query);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysFileDO saveSysFile(SysFileForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysFile(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysFile(Long... ids);
}
