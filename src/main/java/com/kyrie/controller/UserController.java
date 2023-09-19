package com.kyrie.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kyrie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/test")
@DS("#header.userDs")
public class UserController {

    @Autowired
    private UserService crudService;

    @GetMapping("/c")
    public String c(Long id) {
        return crudService.queryUser(id);
    }
}
