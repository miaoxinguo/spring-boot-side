package io.github.miaoxinguo.sbs.dao;

import io.github.miaoxinguo.sbs.entity.Account;
import io.github.miaoxinguo.sbs.modal.PageableRepository;
import java.util.List;

public interface AccountRepository extends PageableRepository {
    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    int insert(Account record);

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    Account selectByPrimaryKey(Integer id);

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    List<Account> selectAll();

    /**
     * 该方法由 Mybatis-Generator 自动创建，不要修改 
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Account record);
}