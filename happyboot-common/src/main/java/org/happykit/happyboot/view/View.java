package org.happykit.happyboot.view;

/**
 * 前端输出不同视图
 *
 * @author shaoqiang
 * @version 1.0 2020/3/13
 */
public class View {
    /**
     * 简单视图
     */
    public interface SimpleView {
    }

    /**
     * 详情视图
     */
    public interface DetailView extends SimpleView {
    }

    /**
     * 权限视图
     */
    public interface SecurityView extends DetailView {
    }
}
