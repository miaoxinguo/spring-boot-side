package com.github.miaoxinguo.mapper;

import com.github.miaoxinguo.sbs.SpringBootSideApp;
import com.github.miaoxinguo.sbs.entity.Account;
import com.github.miaoxinguo.sbs.mapper.AccountMapper;
import com.github.miaoxinguo.sbs.qo.AccountQueryObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestAccountMapper {
    private static final Logger logger = LoggerFactory.getLogger(TestAccountMapper.class);

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testSelectById() {
        Account account = accountMapper.selectById(1);
        logger.info("result is {}", account);
    }

    @Test
    public void testSelectListByQueryObject() {
        AccountQueryObject qo = new AccountQueryObject();
        qo.setPageNum(1);
        qo.setPageSize(12);
        List<Account> result = accountMapper.selectByPageableQo(qo);
        // System.out.println(TotalCountHolder.get());
        System.out.println(result);
    }

}
