package com.github.miaoxinguo.sbs.mapper;

import com.github.miaoxinguo.mybatis.plugin.PagingResult;
import com.github.miaoxinguo.sbs.SpringBootSideApp;
import com.github.miaoxinguo.sbs.entity.Account;
import com.github.miaoxinguo.sbs.qo.AccountQueryObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestAccountRepository {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testSelectById() {
        Account account = accountMapper.selectById(1);
        System.out.println(account);
    }

    @Test
    public void testSelectListByQueryObject() {
        AccountQueryObject qo = new AccountQueryObject();
        qo.setPageNum(1);
        qo.setPageSize(12);
        PagingResult result = accountMapper.selectByPagableQo(qo);
        System.out.println(result);
    }

}
