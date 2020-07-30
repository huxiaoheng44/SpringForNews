package com.hxh.news.service.Impl;

import com.hxh.news.dao.UserRepository;
import com.hxh.news.po.User;
import com.hxh.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUsers(String username, String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }
}
