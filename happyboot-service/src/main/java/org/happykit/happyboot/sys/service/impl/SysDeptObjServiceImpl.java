package org.happykit.happyboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.happykit.happyboot.constant.CommonConstant;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.sys.factory.SysDeptObjFactory;
import org.happykit.happyboot.sys.mapper.SysDeptObjMapper;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.form.SysDeptObjForm;
import org.happykit.happyboot.sys.model.form.SysDeptObjModifyNodeForm;
import org.happykit.happyboot.sys.model.query.SysDeptObjQueryParam;
import org.happykit.happyboot.sys.service.SysDeptObjService;
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
 * 对象内部部门表 服务实现类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Validated(Default.class)
@Service
public class SysDeptObjServiceImpl extends ServiceImpl<SysDeptObjMapper, SysDeptObjDO> implements SysDeptObjService {
    @Override
    public List<TreeNodeDTO> tree() {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(SysConstant.ROOT_PARENT_ID_STR);
        return buildChildTree(node);
    }

    @Override
    public List<org.happykit.happyboot.model.dto.TreeNodeDTO> tree(Long parentId) {

        List<org.happykit.happyboot.model.dto.TreeNodeDTO> list = new ArrayList<>();

        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, parentId);
        queryWrapper.orderByAsc(SysDeptObjDO::getOrderId);
        List<SysDeptObjDO> sysDeptObjs = list(queryWrapper);

        sysDeptObjs.stream().forEach(deptObj -> {
            org.happykit.happyboot.model.dto.TreeNodeDTO dto = new org.happykit.happyboot.model.dto.TreeNodeDTO();
            dto.setId(deptObj.getId());
            dto.setParentId(deptObj.getParentId());
            dto.setTitle(deptObj.getDeptName());
            dto.setDisabled(false);
            dto.setIsLeadDept(CommonConstant.LEAD_DEPT_FLAG.equals(deptObj.getIsLeadDept()));
            LambdaQueryWrapper<SysDeptObjDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SysDeptObjDO::getParentId, deptObj.getId());
            List<SysDeptObjDO> children = list(queryWrapper2);

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
    public List<SysDeptObjDO> listSysDeptObjs(SysDeptObjQueryParam param) {
        return this.baseMapper.selectSysDeptObjs(param);
    }

    @Override
    public List<SysDeptObjDO> listSysDeptObjsByUserId(String userId) {
        return this.baseMapper.selectSysDeptObjsByUserId(userId);
    }

    @Override
    public List<SysDeptObjDO> listSysDeptObjsByObjIdAndUserId(String objId, String userId) {
        return this.baseMapper.selectSysDeptObjsByObjIdAndUserId(objId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptObjDO saveSysDeptObj(SysDeptObjForm form) {
        SysDeptObjDO entity = SysDeptObjFactory.INSTANCE.form2Do(form);

        SysDeptObjDO parentNode = this.getById(entity.getParentId());
        if (parentNode == null) {
            throw new RuntimeException("未找到父节点");
        }
        entity.setSubjectId(parentNode.getSubjectId());

        // 获取部门编码
        String deptCode = generateDeptCode(entity.getParentId());
        entity.setDeptCode(deptCode);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptObjDO saveSysDeptObjParent(SysDeptObjDO entity) {
        // 获取部门编码
        String deptCode = generateDeptCode(entity.getParentId());
        entity.setDeptCode(deptCode);

        if (!save(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptObjDO updateSysDeptObj(SysDeptObjForm form) {
        SysDeptObjDO entity = SysDeptObjFactory.INSTANCE.form2Do(form);

        if (!updateById(entity)) {
            throw new RuntimeException("保存失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysDeptObjNode(SysDeptObjModifyNodeForm form) {

        SysDeptObjDO deptObj = this.getById(form.getId());
        if (deptObj == null) {
            throw new RuntimeException("未找到节点");
        }
        SysDeptObjDO parentDeptObj = this.getById(form.getParentId());
        if (parentDeptObj == null) {
            throw new RuntimeException("未找到节点");
        }
        if (deptObj.getParentId().equals(form.getParentId())) {
            return true;
        }
        if (!deptObj.getSubjectId().equals(parentDeptObj.getSubjectId())) {
            throw new RuntimeException("禁止跨单位移动节点");
        }

        // 获取部门编码
        String deptCode = generateDeptCode(parentDeptObj.getId());
        SysDeptObjDO entity = new SysDeptObjDO();
        entity.setId(form.getId());
        entity.setParentId(form.getParentId());
        entity.setDeptCode(deptCode);
        boolean rel = updateById(entity);

        // 下级树变更部门编号,先清空下级部门编号，再生成
        cleanChildCode(entity.getId());
        changeChildCode(entity.getId());

        return rel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDeptObj(Long id) {
        SysDeptObjDO sysDeptObj = getById(id);
        if (sysDeptObj == null) {
            return false;
        }
        if (!canDelete(sysDeptObj)) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysDeptObj(Long... ids) {
        List<Long> idList = Arrays.asList(ids);
        idList.stream().forEach(id -> {
            deleteSysDeptObj(id);
        });

        return true;
    }

    /**
     * 生成部门编码
     *
     * @param parentId
     * @return
     */
    private String generateDeptCode(String parentId) {

        LambdaQueryWrapper<SysDeptObjDO> query = new LambdaQueryWrapper<>();

        if (SysConstant.ROOT_PARENT_ID_STR == parentId) {
            query.eq(SysDeptObjDO::getParentId, SysConstant.ROOT_PARENT_ID);
            query.orderByDesc(SysDeptObjDO::getDeptCode);
            List<SysDeptObjDO> objDeptList = this.list(query);
            if (CollectionUtils.isEmpty(objDeptList)) {
                return YouBianCodeUtils.getNextYouBianCode(null);
            } else {
                return YouBianCodeUtils.getNextYouBianCode(objDeptList.get(0).getDeptCode());
            }
        } else {
            SysDeptObjDO sysDeptObj = this.getById(parentId);
            if (sysDeptObj == null) {
                throw new RuntimeException("未找到上级部门");
            }
            query.eq(SysDeptObjDO::getParentId, parentId);
            query.orderByDesc(SysDeptObjDO::getDeptCode);
            List<SysDeptObjDO> objDeptList = this.list(query);
            if (CollectionUtils.isEmpty(objDeptList)) {
                return YouBianCodeUtils.getSubYouBianCode(sysDeptObj.getDeptCode(), null);
            } else {
                return YouBianCodeUtils.getSubYouBianCode(sysDeptObj.getDeptCode(), objDeptList.get(0).getDeptCode());
            }
        }
    }

    /**
     * 递归更新部门编号
     *
     * @param parentId
     */
    private void changeChildCode(String parentId) {
        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, parentId);
        List<SysDeptObjDO> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(deptObj -> {
                // 获取部门编码
                String deptCode = generateDeptCode(deptObj.getParentId());
                SysDeptObjDO entity = new SysDeptObjDO();
                entity.setId(deptObj.getId());
                entity.setDeptCode(deptCode);
                updateById(entity);

                changeChildCode(deptObj.getId());
            });
        }
    }

    /**
     * 递归清空部门编号
     *
     * @param parentId
     */
    private void cleanChildCode(String parentId) {
        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, parentId);
        List<SysDeptObjDO> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(deptObj -> {
                SysDeptObjDO entity = new SysDeptObjDO();
                entity.setId(deptObj.getId());

                this.baseMapper.updateDeptCodeNull(entity);

                cleanChildCode(deptObj.getId());
            });
        }
    }

    private List<TreeNodeDTO> buildChildTree(TreeNodeDTO node) {
        List<TreeNodeDTO> list = new ArrayList<>();

        LambdaQueryWrapper<SysDeptObjDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeptObjDO::getParentId, node.getId());
        queryWrapper.orderByAsc(SysDeptObjDO::getOrderId);
        List<SysDeptObjDO> sysDeptObjs = list(queryWrapper);

        sysDeptObjs.stream().forEach(deptObj -> {
            TreeNodeDTO dto = new TreeNodeDTO();
            dto.setId(deptObj.getId());
            dto.setParentId(deptObj.getParentId());
            dto.setTitle(deptObj.getDeptName());
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

    /**
     * 判断是否可以删除
     *
     * @param sysDeptObj
     * @return
     */
    private boolean canDelete(SysDeptObjDO sysDeptObj) {
        // 若是根节点，则不能删除
        if (sysDeptObj.getParentId().equals(SysConstant.ROOT_PARENT_ID)) {
            return false;
        }
        return true;
    }

    @Override
    public String getNameById(String id) {
        return this.baseMapper.getNameById(id);
    }
}