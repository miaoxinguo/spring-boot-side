package com.github.miaoxinguo.mybatis.plugin.qo;

/**
 * 分页查询对象抽象类
 */
public abstract class PageableQo {

    private static final int DEFAULT_PAGE_SIZE = 10;

    // 当前页数
    private int pageNum;

    // 每页记录数
    private int pageSize;

    /**
     * 获取偏移量
     */
    public int getOffset() {
        if (pageNum <= 1) {
            return 0;
        }
        return (pageNum - 1) * pageSize;
    }

    /**
     * 获取查询记录数
     */
    public int getLimit() {
        if (pageSize == 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    // getter、setter
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
