package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.form.SysDictForm;
import org.happykit.happyboot.sys.model.query.SysDictPageQueryParam;

import javax.validation.constraints.NotNull;

/**
 * 字典 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
public interface SysDictService extends IService<SysDictDO> {

    /**
     * 通过dictCode获取对象
     *
     * @param dictCode
     * @return
     */
    SysDictDO getByDictCode(String dictCode);

    /**
     * 根据dictCode和itemValue查询itemText
     *
     * @param code
     * @param value
     * @return
     */
    String getTextByCodeAndValue(String code, String value);

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysDictDO> listSysDictsByPage(SysDictPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysDictDO saveSysDict(SysDictForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysDictDO updateSysDict(SysDictForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysDict(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysDict(Long... ids);
}
