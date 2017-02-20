package io.github.miaoxinguo.sbs.repository;

import io.github.miaoxinguo.sbs.qo.BaseQo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询接口
 */
public interface PageableRepository<T, PK extends Serializable, Q extends BaseQo> extends GenericRepository<T, PK> {

    /**
     * 根据 QueryObject 分页查询结果
     */
    List<T> selectByQo(Q qo);

    /**
     * 根据 QueryObject 查询符合条件的记录数
     */
    int selectCountByQo(Q qo);
}
