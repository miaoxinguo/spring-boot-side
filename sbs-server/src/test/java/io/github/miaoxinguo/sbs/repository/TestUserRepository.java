package io.github.miaoxinguo.sbs.repository;

import io.github.miaoxinguo.sbs.SpringBootSideApp;
import io.github.miaoxinguo.sbs.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootSideApp.class)
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User user = userRepository.selectById(1);
        System.out.println(user.toString());
    }
}
