package com.hxh.news.service;

import com.hxh.news.po.News;
import com.hxh.news.vo.NewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

//NewService.java
public interface NewService {

    Page<News> listNew(Pageable pageable, NewQuery newQuery);

    News saveNews(News news);

    News getNew(Long id);

    News updateNew(News news);

    void deleteNew(Long id);
    //主页显示列表
    Page<News> listNew(Pageable pageable);
    //主页推荐新闻
    List<News> listRecommendNewsTop(Integer size);
    //全局搜索
    Page<News> listNew(String query,Pageable pageable);
    //显示新闻
    News getAndConvert(Long id);
    //标签页新闻
    Page<News> listNew(Long tagId,Pageable pageable);
    //年份对应的新闻
    Map<String,List<News>> archiveNews();

    Long newCount();
}
