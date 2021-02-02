package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysTreeDictDO;
import org.happykit.happyboot.sys.model.form.SysTreeDictForm;
import org.happykit.happyboot.web.model.CascaderDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author shaoqiang
 * @version 1.0 2020/5/21
 */
public interface SysTreeDictService extends IService<SysTreeDictDO> {

    /**
     * 通过字典代号查询无限级联对象列表
     *
     * @param dictCode
     * @return
     */
    List<CascaderDTO> listDictCascader(String dictCode);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysTreeDictDO saveSysTreeDict(SysTreeDictForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysTreeDictDO updateSysTreeDict(SysTreeDictForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysTreeDict(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysTreeDict(Long... ids);
}
