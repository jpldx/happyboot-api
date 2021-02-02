package org.happykit.happyboot.handler;

import org.happykit.happyboot.model.model.ImageArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ImageList自定义类型转换器
 *
 * @author chen.xudong
 * @version 1.0 2020/7/11
 * @see ImageArray
 */
// 定义JdbcType类型
@MappedJdbcTypes({JdbcType.VARCHAR})
// 定义JavaType类型，描述了哪些Java数据类型可被拦截
@MappedTypes({ImageArray.class})
@Slf4j
public class ImageListTypeHandler extends BaseTypeHandler<ImageArray> {
    public ImageListTypeHandler() {
        log.info("init ImageListTypeHandler ok");
        System.out.println("init ImageListTypeHandler ok");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ImageArray parameter, JdbcType jdbcType) throws SQLException {
        String value = null;
        if (null != parameter && parameter.size() > 0) {
            value = StringUtils.join(parameter, ",");
        }
        ps.setString(i, value);
    }

    @Override
    public ImageArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return ImageArray.split(str);
    }

    @Override
    public ImageArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return ImageArray.split(str);
    }

    @Override
    public ImageArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return ImageArray.split(str);
    }
}
