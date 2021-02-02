package org.happykit.happyboot.mybatisplus.interceptor;// package com.litong.boot.mybatisplus.interceptor;
//
// import net.sf.jsqlparser.statement.select.*;
// import net.sf.jsqlparser.statement.values.ValuesStatement;
//
/// **
// * @author shaoqiang
// * @version 1.0 2020/6/24
// */
// public class SelectVisitorImpl implements SelectVisitor {
// @Override
// public void visit(PlainSelect plainSelect) {
// // 访问 select
// if (plainSelect.getSelectItems() != null) {
// for (SelectItem item : plainSelect.getSelectItems()) {
// item.accept(new SelectItemVisitorImpl());
// }
// }
//
// }
//
// @Override
// public void visit(SetOperationList setOperationList) {
//
// }
//
// @Override
// public void visit(WithItem withItem) {
//
// }
//
// @Override
// public void visit(ValuesStatement valuesStatement) {
//
// }
// }
