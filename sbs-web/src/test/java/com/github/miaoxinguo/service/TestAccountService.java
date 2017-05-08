package com.github.miaoxinguo.service;

import com.github.miaoxinguo.sbs.SpringBootSideApp;
import com.github.miaoxinguo.sbs.entity.Account;
import com.github.miaoxinguo.sbs.entity.User;
import com.github.miaoxinguo.sbs.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestAccountService {

    @Autowired
    private AccountService accountService;


    @Test
    public void testAdd() throws Exception {
        Account account = new Account();
        account.setName("foo");
        account.setType(1);
        account.setBalance(new BigDecimal("21.2"));
        User user = new User();
        user.setId(1);
        account.setUser(user);

        Integer id = accountService.add(account);

        System.out.println(accountService.getById(id));
    }

    @Test
    public void testFindById() {
        Account account = accountService.getById(1);
        System.out.println(account);
    }
}
