package org.happykit.happyboot.dao;

import java.util.List;
import java.util.Map;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/24
 */
public interface GeneratorDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
