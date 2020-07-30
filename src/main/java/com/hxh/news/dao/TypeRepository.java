package com.hxh.news.dao;

import com.hxh.news.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//TypeRepository.java
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

//    //添加功能
//    Type saveType(Type type);
//    //获取Type信息
//    Type getTypeByName(String name);
}
