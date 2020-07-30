package com.hxh.news.service;

import com.hxh.news.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//TypeService.java
public interface TypeService {

    Page<Type> listType(Pageable pageable);

    Type saveType(Type type);

    Type getTypeByName(String name);

    void delete(Long id);

    Type getType(Long id);

    Type updateType(Long id,Type type);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
