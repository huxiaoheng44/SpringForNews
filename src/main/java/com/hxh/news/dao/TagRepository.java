package com.hxh.news.dao;

import com.hxh.news.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
//TagRepository.java
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
}
