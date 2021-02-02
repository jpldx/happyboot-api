package org.happykit.happyboot.entity;

import java.util.List;

/**
 * 表数据
 *
 * @author shaoqiang
 * @version 1.0 2020/3/24
 */
public class TableEntity {
    //表的名称
    private String tableName;
    //表的备注
    private String comments;
    //表的主键
    private ColumnEntity pk;
    // 表的列名(不包含主键)
    private List<ColumnEntity> columns;

    /**
     * java对象中的字段
     */
    private List<ColumnEntity> javaColumns;

    // 类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    // 类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<ColumnEntity> getJavaColumns() {
        return javaColumns;
    }

    public void setJavaColumns(List<ColumnEntity> javaColumns) {
        this.javaColumns = javaColumns;
    }
}
