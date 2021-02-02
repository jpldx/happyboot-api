package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.model.form.SysDictItemForm;
import org.happykit.happyboot.sys.model.query.SysDictItemPageQueryParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 字典项 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
public interface SysDictItemService extends IService<SysDictItemDO> {

    /**
     * 通过dictId和itemValue获取对象
     *
     * @param dictId
     * @param itemValue
     * @return
     */
    SysDictItemDO getByDictIdAndItemValue(String dictId, String itemValue);

    /**
     * 通过dictId查询列表
     *
     * @param dictId
     * @return
     */
    List<SysDictItemDO> listSysDictItemsByDictId(String dictId);

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysDictItemDO> listSysDictItemsByPage(SysDictItemPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysDictItemDO saveSysDictItem(SysDictItemForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysDictItemDO updateSysDictItem(SysDictItemForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysDictItem(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysDictItem(Long... ids);

    /**
     * 通过字典id删除
     *
     * @param dictId
     * @return
     */
    boolean deleteByDictId(Long dictId);
}
