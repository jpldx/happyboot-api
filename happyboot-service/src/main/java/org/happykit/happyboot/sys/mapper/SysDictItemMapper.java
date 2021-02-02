package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.model.query.SysDictItemPageQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典项表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItemDO> {
    /**
     * 通过dictId和itemValue获取对象
     *
     * @param dictId
     * @param itemValue
     * @return
     */
    SysDictItemDO selectByDictIdAndItemValue(@Param("dictId") String dictId, @Param("itemValue") String itemValue);

    /**
     * 通过dictId查询列表
     *
     * @param dictId
     * @return
     */
    List<SysDictItemDO> selectSysDictItemsByDictId(@Param("dictId") String dictId);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysDictItemDO> selectSysDictItemsByPage(Page page, @Param("param") SysDictItemPageQueryParam param);

}
