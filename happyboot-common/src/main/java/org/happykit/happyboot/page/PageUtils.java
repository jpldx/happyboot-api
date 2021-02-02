package org.happykit.happyboot.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/21
 */
public class PageUtils<T extends PageQuery> implements Serializable {

    private static final String ORDER_TYPE_ASC = "ASC";

    public Page page(T param, Class clazz) {
        Page page = new Page<>(param.getPageNo(), param.getPageSize());
        if (StringUtils.isNotBlank(param.getOrderField())) {
            // 判断排序列是否是输出类的字段,若不是则不排序
            Field field = ReflectionUtils.findField(clazz, param.getOrderField());
            if (field != null) {
                // 驼峰转下划线
                String column = StringUtils.camel2Underline(param.getOrderField());
                boolean isAsc = false;
                if (StringUtils.isNotBlank(param.getOrderType())) {
                    if (param.getOrderType().toUpperCase().indexOf(ORDER_TYPE_ASC) >= 0) {
                        isAsc = true;
                    }
                }
                if (isAsc) {
                    page.setOrders(OrderItem.ascs(column));
                } else {
                    page.setOrders(OrderItem.descs(column));
                }
            }
        }
        return page;
    }
}
