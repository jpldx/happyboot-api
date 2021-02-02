package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.form.SysDeptRegionForm;
import org.happykit.happyboot.sys.model.query.SysDeptRegionQueryParam;
import org.happykit.happyboot.web.model.TreeNodeDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 区域部门表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysDeptRegionService extends IService<SysDeptRegionDO> {

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
    List<TreeNodeDTO> tree(@NotNull Long parentId);

    /**
     * 列表查询
     *
     * @param param
     * @return
     */
    List<SysDeptRegionDO> listSysDeptRegions(SysDeptRegionQueryParam param);

    /**
     * 通过用户id获取区域列表
     *
     * @param userId
     * @return
     */
    List<SysDeptRegionDO> listSysDeptRegionsByUserId(@NotNull String userId);

    /**
     * 通过对象id获取区域列表
     *
     * @param objId
     * @return
     */
    List<SysDeptRegionDO> listSysDeptRegionsByObjId(@NotNull String objId);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysDeptRegionDO saveSysDeptRegion(SysDeptRegionForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysDeptRegionDO updateSysDeptRegion(SysDeptRegionForm form);

    /**
     * 变更节点位置
     *
     * @param form
     * @return
     */
    boolean updateSysDeptRegionNode(ModifyNodeModel form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysDeptRegion(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysDeptRegion(Long... ids);
}
