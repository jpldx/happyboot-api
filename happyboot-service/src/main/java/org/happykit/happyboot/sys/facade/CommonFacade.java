package org.happykit.happyboot.sys.facade;

import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.service.SysDictItemService;
import org.happykit.happyboot.sys.service.SysDictService;
import org.happykit.happyboot.sys.service.SysTreeDictService;
import org.happykit.happyboot.web.model.CascaderDTO;
import org.happykit.happyboot.web.model.SelectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共服务接口
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonFacade {

    private final SysDictService sysDictService;

    private final SysDictItemService sysDictItemService;

    private final SysTreeDictService sysTreeDictService;

    /**
     * 通过字典代号查询字典项列表
     *
     * @param dictCode
     * @return
     */
    public List<SelectDTO> listDictSelect(String dictCode) {
        SysDictDO sysDict = sysDictService.getByDictCode(dictCode);
        if (sysDict == null) {
            return new ArrayList<>();
        }
        List<SysDictItemDO> sysDictItemList = sysDictItemService.listSysDictItemsByDictId(sysDict.getId());
        return sysDictItemList.stream().map(m -> new SelectDTO(m.getItemText(), m.getItemValue())).collect(Collectors.toList());
    }

    /**
     * 通过字典代号查询树状字典
     *
     * @param dictCode
     * @return
     */
    public List<CascaderDTO> listDictCascader(String dictCode) {
        return sysTreeDictService.listDictCascader(dictCode);
    }
}
