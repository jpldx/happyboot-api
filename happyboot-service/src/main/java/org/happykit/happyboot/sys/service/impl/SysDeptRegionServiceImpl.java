package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.factory.SysDeptRegionFactory;
import org.happykit.happyboot.sys.mapper.SysDeptRegionMapper;
import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.form.SysDeptRegionForm;
import org.happykit.happyboot.sys.model.query.SysDeptRegionQueryParam;
import org.happykit.happyboot.sys.service.SysDeptRegionService;
import org.happykit.happyboot.util.YouBianCodeUtils;
import org.happykit.happyboot.web.model.TreeNodeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 区域部门表 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated(Default.class)
@Service
public class SysDeptRegionServiceImpl extends ServiceImpl<SysDeptRegionMapper, SysDeptRegionDO>
        implements SysDeptRegionService {

    @Override
    public List<TreeNodeDTO> tree() {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildChildTree(node);
    }

    @Override
    public List<TreeNodeDTO> tree(Long parentId) {
        List<TreeNodeDTO> list = new ArrayList<>();

        LambdaQueryWrapper<SysDeptRegionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptRegionDO::getParentId, parentId);
        queryWrapper.orderByAsc(SysDeptRegionDO::getOrderId);
        List<SysDeptRegionDO> sysDeptRegions = list(queryWrapper);

        sysDeptRegions.stream().forEach(region -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(region.getId());
            dto.setParentId(region.getParentId());
            dto.setTitle(region.getRegionName());
            dto.setDisabled(false);

            LambdaQueryWrapper<SysDeptRegionDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SysDeptRegionDO::getParentId, region.getId());
            List<SysDeptRegionDO> children = list(queryWrapper2);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            list.add(dto);
        });
        return list;
    }

    @Override
    public List<SysDeptRegionDO> listSysDeptRegions(SysDeptRegionQueryParam param) {
        return this.baseMapper.selectSysDeptRegions(param);
    }

    @Override
    public List<SysDeptRegionDO> listSysDeptRegionsByUserId(String userId) {
        return this.baseMapper.selectSysDeptRegionsByUserId(userId);
    }

    @Override
    public List<SysDeptRegionDO> listSysDeptRegionsByObjId(String objId) {
        return this.baseMapper.selectSysDeptRegionsByObjId(objId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptRegionDO saveSysDeptRegion(SysDeptRegionForm form) {
        SysDeptRegionDO entity = SysDeptRegionFactory.INSTANCE.form2Do(form);

        if (!entity.getParentId().equals(SysConstant.ROOT_PARENT_ID)) {
            SysDeptRegionDO parentRegion = this.getById(form.getParentId());
            if (parentRegion == null) {
                throw new RuntimeException("未找到节点");
            }
        }

        // 获取编码
        String regionCode = generateRegionCode(entity.getParentId());
        entity.setRegionCode(regionCode);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptRegionDO updateSysDeptRegion(SysDeptRegionForm form) {
        SysDeptRegionDO entity = SysDeptRegionFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysDeptRegionNode(ModifyNodeModel form) {
        SysDeptRegionDO region = this.getById(form.getId());
        if (region == null) {
            throw new RuntimeException("未找到节点");
        }
        if (region.getParentId().equals(form.getParentId())) {
            return true;
        }

        if (!region.getParentId().equals(SysConstant.ROOT_PARENT_ID)) {
            SysDeptRegionDO parentRegion = this.getById(form.getParentId());
            if (parentRegion == null) {
                throw new RuntimeException("未找到节点");
            }
        }

        String regionCode = generateRegionCode(form.getParentId());
        SysDeptRegionDO entity = new SysDeptRegionDO();
        entity.setId(form.getId());
        entity.setParentId(form.getParentId());
        entity.setRegionCode(regionCode);
        boolean rel = updateById(entity);

        // 下级树变更部门编号,先清空下级部门编号，再生成
        cleanChildCode(entity.getId());
        changeChildCode(entity.getId());

        return rel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDeptRegion(Long id) {
        SysDeptRegionDO sysDeptRegion = getById(id);
        if (sysDeptRegion == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDeptRegion(Long... ids) {
        List idList = Arrays.asList(ids);
        return this.removeByIds(idList);
    }

    /**
     * 生成区域编码
     *
     * @param parentId
     * @return
     */
    private String generateRegionCode(String parentId) {

        LambdaQueryWrapper<SysDeptRegionDO> query = new LambdaQueryWrapper<>();

        if (SysConstant.ROOT_PARENT_ID_STR == parentId) {
            query.eq(SysDeptRegionDO::getParentId, SysConstant.ROOT_PARENT_ID);

            query.orderByDesc(SysDeptRegionDO::getRegionCode);
            List<SysDeptRegionDO> regionList = this.list(query);
            if (CollectionUtils.isEmpty(regionList)) {
                return YouBianCodeUtils.getNextYouBianCode(null);
            } else {
                return YouBianCodeUtils.getNextYouBianCode(regionList.get(0).getRegionCode());
            }
        } else {

            SysDeptRegionDO sysDeptRegion = this.getById(parentId);
            if (sysDeptRegion == null) {
                throw new RuntimeException("未找到上级部门");
            }
            query.eq(SysDeptRegionDO::getParentId, parentId);
            query.orderByDesc(SysDeptRegionDO::getRegionCode);
            List<SysDeptRegionDO> regionList = this.list(query);
            if (CollectionUtils.isEmpty(regionList)) {
                return YouBianCodeUtils.getSubYouBianCode(sysDeptRegion.getRegionCode(), null);
            } else {
                return YouBianCodeUtils.getSubYouBianCode(sysDeptRegion.getRegionCode(),
                        regionList.get(0).getRegionCode());
            }
        }
    }

    /**
     * 递归更新部门编号
     *
     * @param parentId
     */
    private void changeChildCode(String parentId) {
        LambdaQueryWrapper<SysDeptRegionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptRegionDO::getParentId, parentId);
        List<SysDeptRegionDO> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(region -> {
                String regionCode = generateRegionCode(region.getParentId());
                SysDeptRegionDO entity = new SysDeptRegionDO();
                entity.setId(region.getId());
                entity.setRegionCode(regionCode);
                updateById(entity);

                changeChildCode(region.getId());
            });
        }
    }

    /**
     * 递归清空部门编号
     *
     * @param parentId
     */
    private void cleanChildCode(String parentId) {
        LambdaQueryWrapper<SysDeptRegionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptRegionDO::getParentId, parentId);
        List<SysDeptRegionDO> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(region -> {
                SysDeptRegionDO entity = new SysDeptRegionDO();
                entity.setId(region.getId());
                this.baseMapper.updateRegionCodeNull(entity);

                cleanChildCode(region.getId());
            });
        }
    }

    private List<TreeNodeDTO> buildChildTree(TreeNodeDTO node) {
        List<TreeNodeDTO> list = new ArrayList<>();
        List<SysDeptRegionDO> sysDeptRegions = this.baseMapper.selectSysDeptRegionsByParentId(node.getId());
        sysDeptRegions.stream().forEach(region -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(region.getId());
            dto.setParentId(region.getParentId());
            dto.setTitle(region.getRegionName());
            dto.setDisabled(false);
            List<TreeNodeDTO> children = buildChildTree(dto);
            dto.setChildren(children);
            if (CollectionUtils.isEmpty(children)) {
                dto.setLeaf(true);
            } else {
                dto.setLeaf(false);
            }
            list.add(dto);
        });
        return list;
    }
}