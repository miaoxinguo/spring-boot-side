package demo.spring.boot.controller;

import demo.spring.boot.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String getUser(@Valid User user, BindingResult result) {
        System.out.println(result);
        return user.getName();
    }
}
