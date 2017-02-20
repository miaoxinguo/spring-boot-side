package io.github.miaoxinguo.sbs.qo;

/**
 * 分页查询抽象类
 */
public abstract class PageableQo extends BaseQo {

    // 当前页数
//    @Digits(message = "pageNum 必须是正整数", integer = Integer.MAX_VALUE, fraction = 0)
//    @Min(message = "页数最小值为{value}", value = 1)
    private int pageNum;

    // 每页记录数
//    @Digits(message = "pageSize 必须是正整数", integer = Integer.MAX_VALUE, fraction = 0)
//    @Max(message = "每页记录数不能大于{value}条", value = 500)
//    @Min(message = "最小值不能小于{value}", value = 1)
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum < 1 ? 1 : pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    // set 方法校验是为了防止 Controller 中没加 @Valid
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < 1 ? 1 : pageSize;
        this.pageSize = pageSize > 500 ? 500 : pageSize;
    }

    /**
     * 获取偏移量
     */
    protected int getOffset() {
        return (pageNum -1) * pageSize;
    }

    /**
     * 获取查询记录数
     */
    protected int getLimit() {
        return pageSize;
    }

}
