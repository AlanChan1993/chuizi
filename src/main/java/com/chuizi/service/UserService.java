package com.chuizi.service;

import com.chuizi.entiy.User;

import java.util.List;

public interface UserService {
    /* List<User> findAll();
     User findByName(String name);
     String findPswByName(String UserName);
     void save(User user);*/
    String login(User user);

    String regist(User user);

    List<User> findAll();
}
