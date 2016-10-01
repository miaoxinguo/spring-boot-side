package io.github.miaoxinguo.sbs.dao;

import io.github.miaoxinguo.sbs.entity.User;
import io.github.miaoxinguo.sbs.modal.PageableRepository;
import java.util.List;

public interface UserRepository extends PageableRepository {
    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);
}