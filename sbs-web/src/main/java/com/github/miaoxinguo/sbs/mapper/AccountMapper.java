package com.github.miaoxinguo.sbs.mapper;

import com.github.miaoxinguo.mybatis.plugin.mapper.PageableMapper;
import com.github.miaoxinguo.sbs.entity.Account;
import com.github.miaoxinguo.sbs.qo.AccountQueryObject;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper extends PageableMapper<Account, Integer, AccountQueryObject> {
}
