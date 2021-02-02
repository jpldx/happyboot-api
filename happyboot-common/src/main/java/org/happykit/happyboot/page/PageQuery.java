package org.happykit.happyboot.page;


import javax.validation.constraints.NotNull;

/**
 * 分页查询基类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/21
 */
public abstract class PageQuery extends TimeQuery {
    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Long pageNo = 1L;
    /**
     * 页数
     */
    @NotNull(message = "页数不能为空")
    private Long pageSize = 10L;
    /**
     * 排序方式
     */
    private String orderType;
    /**
     * 排序字段
     */
    private String orderField;


    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
}