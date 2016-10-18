package io.github.miaoxinguo.sbs.service;

import io.github.miaoxinguo.sbs.SpringBootSideApp;
import io.github.miaoxinguo.sbs.entity.Account;
import io.github.miaoxinguo.sbs.entity.User;
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
    public void testAdd(){
        Account account = new Account();
        account.setName("foo");
        account.setType(1);
        account.setBalance(new BigDecimal("21.2"));
        User user = new User();
        user.setId(1);
        account.setUser(user);

        accountService.add(account);
    }

    @Test
    public void testFindById() {
        Account account = accountService.getById(1);
        System.out.println(account);
    }
}
