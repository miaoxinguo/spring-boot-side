package demo.spring.boot.controller;

import demo.spring.boot.entity.User;
import demo.spring.boot.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String getUser(long userId) {
        System.out.println(userId);
        User user = userMapper.selectByPrimaryKey(userId);
        return user.toString();
    }
}
