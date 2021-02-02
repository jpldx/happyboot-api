package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.form.SysDeptObjForm;
import org.happykit.happyboot.sys.model.form.SysDeptObjModifyNodeForm;
import org.happykit.happyboot.sys.model.query.SysDeptObjQueryParam;
import org.happykit.happyboot.web.model.TreeNodeDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 对象内部部门表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysDeptObjService extends IService<SysDeptObjDO> {

    /**
     * 树结构数据
     *
     * @return
     */
    List<TreeNodeDTO> tree();

    /**
     * 通过父id查询树列表
     *
     * @param parentId
     * @return
     */
    List<org.happykit.happyboot.model.dto.TreeNodeDTO> tree(@NotNull Long parentId);

    /**
     * 列表查询
     *
     * @param param
     * @return
     */
    List<SysDeptObjDO> listSysDeptObjs(SysDeptObjQueryParam param);

    /**
     * 通过用户id获取部门列表
     *
     * @param userId
     * @return
     */
    List<SysDeptObjDO> listSysDeptObjsByUserId(@NotNull String userId);

    /**
     * 通过单位ID、用户id获取部门列表
     *
     * @param objId
     * @param userId
     * @return
     */
    List<SysDeptObjDO> listSysDeptObjsByObjIdAndUserId(@NotNull String objId, @NotNull String userId);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysDeptObjDO saveSysDeptObj(SysDeptObjForm form);

    /**
     * 保存根节点
     *
     * @param entity
     * @return
     */
    SysDeptObjDO saveSysDeptObjParent(SysDeptObjDO entity);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysDeptObjDO updateSysDeptObj(SysDeptObjForm form);

    /**
     * 变更节点位置
     *
     * @param form
     * @return
     */
    boolean updateSysDeptObjNode(SysDeptObjModifyNodeForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysDeptObj(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysDeptObj(Long... ids);

    /**
     * 根据部门id查询部门名称
     *
     * @param id 部门id
     * @return
     */
    String getNameById(String id);
}
