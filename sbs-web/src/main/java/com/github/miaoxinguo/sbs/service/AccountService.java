package com.github.miaoxinguo.sbs.service;

import com.github.miaoxinguo.sbs.entity.Account;
import com.github.miaoxinguo.sbs.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Transactional
    public Integer add(Account account) {
        // return accountMapper.insert(account);
        accountMapper.insert(account);

        Integer i = add2(account);
        if (i > 0) {
            throw new RuntimeException();
        }
        return i;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer add2(Account account) {
        return accountMapper.insert(account);
    }


    public Account getById(Integer id) {
        return accountMapper.selectById(id);
    }
}
