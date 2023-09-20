package com.kyrie.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kyrie.pojo.User;
import com.kyrie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find/#{id}")
    public String f(Long id) {
        return userService.queryUser(id);
    }

    @PostMapping("/add")
    public String a(@RequestBody User user){
        return userService.add(user);
    }
}
