package com.hxh.news.service;

import com.hxh.news.po.News;
import com.hxh.news.vo.NewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//NewService.java
public interface NewService {

    Page<News> listNew(Pageable pageable, NewQuery newQuery);

    News saveNews(News news);

    News getNew(Long id);

    News updateNew(News news);

    void deleteNew(Long id);
    //主页显示列表
    Page<News> listNew(Pageable pageable);
}
