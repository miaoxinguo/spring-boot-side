package com.github.miaoxinguo.mybatis.plugin;

import java.util.List;

/**
 * 分页查询结果
 */
public class Page<T> {

    // 当前页
    private int current = 1;

    // 页大小
    private int size;

    // 总页数
    private int totalPage;

    // 总记录数
    private int totalRecord;

    // 分页查询结果
    private List<T> list;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
