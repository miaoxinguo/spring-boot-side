package com.github.miaoxinguo.mapper;

import com.github.miaoxinguo.mybatis.plugin.Page;
import com.github.miaoxinguo.sbs.SpringBootSideApp;
import com.github.miaoxinguo.sbs.entity.User;
import com.github.miaoxinguo.sbs.mapper.UserMapper;
import com.github.miaoxinguo.sbs.qo.UserQueryObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestUserMapper {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testFindById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void testSelectByPageableQo() {
        UserQueryObject qo = new UserQueryObject();
        qo.setPageNum(1);
        qo.setPageSize(10);
        Page<User> result = userMapper.selectByPageableQo(qo);
        System.out.println(result);
    }
}
