package com.hxh.news.service;

import com.hxh.news.po.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User checkUsers(String username, String password);
}
