package io.github.miaoxinguo.sbs.modal;

/**
 * 分页查询抽象类
 */
public abstract class PageQo extends BaseQo{

    // 当前页数
    private int pageNum;

    // 每页记录数
    private int pageSize;

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
