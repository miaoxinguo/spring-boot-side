package io.github.miaoxinguo.sbs.modal;

import java.util.List;

/**
 * 分页查询接口
 */
public interface PageableRepository<T extends PageQueryObject> {

    /**
     * 根据 QueryObject 分页查询结果
     */
    List<T> selectByQueryObject(T qo);
}
