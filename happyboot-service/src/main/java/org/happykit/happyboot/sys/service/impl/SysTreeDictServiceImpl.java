package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.sys.factory.SysTreeDictFactory;
import org.happykit.happyboot.sys.mapper.SysTreeDictMapper;
import org.happykit.happyboot.sys.model.entity.SysTreeDictDO;
import org.happykit.happyboot.sys.model.form.SysTreeDictForm;
import org.happykit.happyboot.sys.service.SysTreeDictService;
import org.happykit.happyboot.web.model.CascaderDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 树状数据字典 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/5/21
 */
@CacheConfig(cacheNames = {"sysTreeDict"})
@Validated(Default.class)
@Service
public class SysTreeDictServiceImpl extends ServiceImpl<SysTreeDictMapper, SysTreeDictDO>
        implements SysTreeDictService {

    @Override
    @Cacheable(key = "'listDictCascader_'+#dictCode")
    public List<CascaderDTO> listDictCascader(String dictCode) {
        CascaderDTO node = new CascaderDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildChildTree(node, dictCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTreeDictDO saveSysTreeDict(SysTreeDictForm form) {
        SysTreeDictDO entity = SysTreeDictFactory.INSTANCE.form2Do(form);
        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTreeDictDO updateSysTreeDict(SysTreeDictForm form) {
        SysTreeDictDO entity = SysTreeDictFactory.INSTANCE.form2Do(form);
        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysTreeDict(@NotNull Long id) {
        SysTreeDictDO sysTreeDict = getById(id);
        if (sysTreeDict == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysTreeDict(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    /**
     * 递归构建树
     *
     * @param node
     * @param dictCode
     * @return
     */
    private List<CascaderDTO> buildChildTree(CascaderDTO node, String dictCode) {
        List<SysTreeDictDO> sysTreeDictList =
                this.baseMapper.selectSysTreeDictsByDictCodeAndParentCode(dictCode, node.getId());
        List<CascaderDTO> list = new ArrayList<>();
        sysTreeDictList.stream().forEach(sysTreeDict -> {
            CascaderDTO dto = new CascaderDTO();
            dto.setId(sysTreeDict.getCode());
            dto.setParentId(sysTreeDict.getParentCode());
            dto.setLabel(sysTreeDict.getDictName());

            List<CascaderDTO> children = buildChildTree(dto, dictCode);
            if (CollectionUtils.isEmpty(children)) {
                dto.setChildren(null);
            } else {
                dto.setChildren(children);
            }
            list.add(dto);
        });
        return list;
    }
}
