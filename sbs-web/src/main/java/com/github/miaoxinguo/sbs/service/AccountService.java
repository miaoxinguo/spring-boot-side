package com.github.miaoxinguo.sbs.service;

import com.github.miaoxinguo.sbs.mapper.AccountMapper;
import com.github.miaoxinguo.sbs.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional
    public Integer add(Account account) {
        return accountMapper.insert(account);
    }

    @Transactional
    public Account getById(Integer id) {
        return accountMapper.selectById(id);
    }
}
