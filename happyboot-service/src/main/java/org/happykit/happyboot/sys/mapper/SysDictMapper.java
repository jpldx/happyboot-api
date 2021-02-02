package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.query.SysDictPageQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
public interface SysDictMapper extends BaseMapper<SysDictDO> {
    /**
     * 通过dictCode获取对象
     *
     * @param dictCode
     * @return
     */
    SysDictDO selectByDictCode(@Param("dictCode") String dictCode);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysDictDO> selectSysDictsByPage(Page page, @Param("param") SysDictPageQueryParam param);


    String getTextByCodeAndValue(@Param("code") String code, @Param("value") String value);
}
