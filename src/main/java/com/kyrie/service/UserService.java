package com.kyrie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyrie.pojo.User;

public interface UserService extends IService<User> {
    String queryUser(Long id);

    String add(User user);
}
