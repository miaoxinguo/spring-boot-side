package io.github.miaoxinguo.sbs.repository;

import java.io.Serializable;

/**
 * 分页查询接口
 */
public interface GenericRepository<T, PK extends Serializable> {

    /**
     * 插入一条记录
     */
    PK insert(T t);

    /**
     * 根据 id 删除
     */
    int deleteById(PK id);

    /**
     * 更新
     */
    int update(T t);

    /**
     * 更新 - 只更新不为 null 或 0（int） 的字段
     */
    int updateSelective(T t);

    /**
     * 根据 id 查询
     */
    T selectById(PK id);

}
