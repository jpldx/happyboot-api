package org.happykit.happyboot.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.demo.model.entity.DemoLonglatDO;

import java.util.List;

/**
 * @author chen.xudong
 * @version 1.0 2020/7/11
 */
public interface DemoLonglatMapper extends BaseMapper<DemoLonglatDO> {

    List<DemoLonglatDO> list();
}
