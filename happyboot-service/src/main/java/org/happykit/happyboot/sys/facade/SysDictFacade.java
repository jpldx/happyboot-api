package org.happykit.happyboot.sys.facade;

import com.baomidou.mybatisplus.core.metadata.IPage;

import org.happykit.happyboot.sys.model.dto.DictSelectDTO;
import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.model.form.SysDictForm;
import org.happykit.happyboot.sys.model.query.SysDictPageQueryParam;
import org.happykit.happyboot.sys.service.SysDictItemService;
import org.happykit.happyboot.sys.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDictFacade {

    private final SysDictService sysDictService;

    private final SysDictItemService sysDictItemService;

    /**
     * 新增字典
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysDictDO saveSysDict(SysDictForm form) {
        return sysDictService.saveSysDict(form);
    }

    /**
     * 修改字典
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SysDictDO updateSysDict(SysDictForm form) {
        return sysDictService.updateSysDict(form);
    }

    /**
     * 删除字典
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDict(Long... ids) {
        for (Long id : ids) {
            sysDictItemService.deleteByDictId(id);
        }
        return sysDictService.deleteSysDict(ids);
    }


    /**
     * 通过字典代码和字典项值获取字典项名称
     *
     * @param dictCode
     * @param itemValue
     * @return
     */
    public String getItemTextByDictCodeAndItemValue(String dictCode, String itemValue) {
        SysDictDO sysDict = sysDictService.getByDictCode(dictCode);
        if (sysDict == null) {
            return "";
        }
        SysDictItemDO sysDictItem = sysDictItemService.getByDictIdAndItemValue(sysDict.getId(), itemValue);
        if (sysDictItem != null) {
            return sysDictItem.getItemText();
        }
        return "";
    }

    /**
     * 分页查询字典
     *
     * @param param
     * @return
     */
    public IPage<SysDictDO> listSysDictsByPage(SysDictPageQueryParam param) {
        return sysDictService.listSysDictsByPage(param);
    }

    /**
     * 通过id获取字典对象
     *
     * @param id
     * @return
     */
    public SysDictDO getById(Long id) {
        return sysDictService.getById(id);
    }

    /**
     * 获取字典列表
     *
     * @return
     */
    public List<SysDictDO> list() {
        return sysDictService.list();
    }

    /**
     * 通过字典代号查询字典项列表
     *
     * @param dictCode
     * @return
     */
    public List<DictSelectDTO> listDictSelect(String dictCode) {
        SysDictDO sysDict = sysDictService.getByDictCode(dictCode);
        if (sysDict == null) {
            return new ArrayList<>();
        }

        List<SysDictItemDO> sysDictItemList = sysDictItemService.listSysDictItemsByDictId(sysDict.getId());
        return sysDictItemList.stream().map(m -> new DictSelectDTO(m.getItemText(), m.getItemValue())).collect(Collectors.toList());
    }

}
