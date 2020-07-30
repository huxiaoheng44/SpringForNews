package com.hxh.news.dao;

import com.hxh.news.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
//UserService.java
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
