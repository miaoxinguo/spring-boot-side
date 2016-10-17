package io.github.miaoxinguo.sbs.repository;

import io.github.miaoxinguo.sbs.SpringBootSideApp;
import io.github.miaoxinguo.sbs.entity.Account;
import io.github.miaoxinguo.sbs.qo.AccountQueryObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestAccountRepository {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSelectById() {
        Account account = accountRepository.selectById(1);
        System.out.println(account);
    }

    @Test
    public void testSelectListByQueryObject() {
        AccountQueryObject qo = new AccountQueryObject();
        qo.setPageNum(1);
        qo.setPageSize(12);
        List<Account> list = accountRepository.selectByQueryObject(qo);
        System.out.println(list);
    }

}
