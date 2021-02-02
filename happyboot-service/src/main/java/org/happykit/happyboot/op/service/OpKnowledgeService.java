package org.happykit.happyboot.op.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.op.model.entity.OpKnowledgeDO;
import org.happykit.happyboot.op.model.form.OpKnowledgeForm;
import org.happykit.happyboot.op.model.query.OpKnowledgePageQuery;

/**
 * 知识库
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
public interface OpKnowledgeService extends IService<OpKnowledgeDO> {

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<OpKnowledgeDO> page(OpKnowledgePageQuery query);

    /**
     * 新增
     *
     * @param form
     * @return
     */
    boolean add(OpKnowledgeForm form);

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    boolean update(OpKnowledgeForm form);

//	/**
//	 * 删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	boolean deleteSysKnowledge(@NotNull String id);
//
//	/**
//	 * 批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	boolean deleteSysKnowledge(String... ids);
//
//    /**
//     * 发布
//     * @param id
//     * @return
//     */
//    boolean sendSysKnowledge(String id);

//    /**
//     * 撤回
//     * @param id
//     * @return
//     */
//    boolean backSysKnowledge(String id);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    OpKnowledgeDO get(String id);
}

