package com.github.miaoxinguo.sbs.mapper;

import com.github.miaoxinguo.sbs.SpringBootSideApp;
import com.github.miaoxinguo.sbs.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestUserRepository {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

}
